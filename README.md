# FreshExpress(FX)


## 1. 프로젝트 정의

### 1.1 프로젝트 정체성

FreshExpress(FX)는 LOT 기반 재고 추적과 예약 중심 출고 구조를 통해 신선식품의 품질을 보장하는 Cold-Chain 물류 관리 시스템

---

### 1.2 해결하려는 문제

기존 물류 시스템의 문제:
- 출고 시점에 유통기한 고려가 미흡
- 출고 차감 이력 추적 불충분
- 상태 변경의 감사 로그 부재
- 예약 없이 즉시 차감 → 동시성 문제 발생

FX는 이를 다음 방식으로 해결한다:

- 예약 → 확정 출고 2단계 구조로 동시성 문제 해결
- 재고의 변경 사항을 기록해 이력 추적
- 상태 변경의 감사를 위한 로그 생성

---

### 1.3 MVP 목표

> 입고 → 재고관리 → 주문 → 예약 → 출고 → 배송 → 완료  

전 과정을 신선과 유통기한의 관점에서 관리할 수 있는 구조로 구현

---

### 1.4 MVP 성공 기준

- 주문 시 자동 FIFO/FEFO 할당
- 출고 예약 구조 정상 동작
- 재고 이동은 반드시 StockMovement 기록
- 상태 전이 오류 발생 시 차단
- 동시 주문 시 재고 음수 방지
- 모든 주요 이벤트 로그 저장

---

## 2. MVP 설계 핵심 원칙

1. Inventory = Lot 기반
2. 출고 = Reservation → Confirm 구조 
3. 모든 상태 변경 기록
4. FIFO 기본 / FEFO 옵션 가능 구조 
5. 동시성은 예약 단계에서 통제

---

## 3. 도메인 구조 (정리 및 계층화)

### 3.1 Core Domain

- Product
- Category
- Warehouse
- Location
- StockLot
- StockReservation
- StockMovement
- Order
- OrderItem
- Delivery

### 3.2 Support Domain

- User
- Role
- Notification

---

## 4. 재고 구조 (정책 통합)

### 4.1 재고 생명주기

입고(IN)
→ 가용 재고
→ 예약(RESERVED)
→ 출고 확정(CONFIRMED)
→ 배송
→ 완료

또는

→ 폐기(DISCARD)

---

### 4.2 StockLot 정책

#### 상태 체계 정리

- NORMAL
- EXPIRING_SOON (D-3 자동 변경)
- EXPIRED
- DISCARDED

#### 상태 자동 변경 규칙

- expiredAt - today ≤ 3 → EXPIRING_SOON
- expiredAt < today → EXPIRED

---

### 4.3 StockReservation 정책

예약 상태:

- RESERVED
- CONFIRMED
- RELEASED
#### 규칙

- 주문 생성 시 예약
- 출고 확정 시 CONFIRMED
- 주문 취소 시 RELEASED
- 예약 만료시간(예: 30분) 설정 가능

---

### 4.4 StockMovement 정책

|타입|발생 시점|
|---|---|
|IN|입고|
|OUT|출고 확정|
|DISCARD|폐기|
|RELEASE|예약 해제|

StockLot.currentQuantity는 Movement 결과로만 변경된다.

---

## 5. 주문-출고-배송 상태 체계 통합

### 5.1 주문 상태
```text
PENDING → PREPARING → SHIPPED → IN_DELIVERY → DELIVERED

            ↓

        CANCELLED / FAILED
```
#### 상태 정의 정제

- PENDING: 주문 생성
- PREPARING: 재고 예약 완료
- SHIPPED: 출고 확정
- IN_DELIVERY: 기사 인수
- DELIVERED: 수령 완료
- CANCELLED: 출고 전 취소
- FAILED: 배송 실패

---

### 5.2 상태 전이 제약

- PREPARING 이후 CANCEL 불가
- SHIPPED 이전만 RELEASE 가능
- DELIVERY 실패 시 FAILED로 종료

---

## 6. 권한 모델 (RBAC 정제)

|역할|가능 행위|
|---|---|
|ADMIN|전체|
|WAREHOUSE_MANAGER|입고, 폐기, 출고 승인|
|PACKAGE_MANAGER|예약 확인, 피킹|
|DRIVER|배송 상태 변경|
|CUSTOMER|주문 생성, 조회|

---

## 7. 알림 정책 통합
### 자동 생성 조건
- EXPIRING_SOON
- 안전재고 이하
- 배송 지연 (IN_DELIVERY 24시간 초과)

---

## 8. 동시성 전략

- StockLot 조회 시 `PESSIMISTIC_WRITE` 또는 Version 기반 Optimistic Lock
- 예약 시점에 재고 차감은 하지 않음
- 확정 시 차감 + Movement 기록

---

## 9. 대시보드 지표 확정

MVP 집계 API 제공:

- 총 가용 재고
- EXPIRING_SOON 수량
- 오늘 주문 수
- 오늘 출고 수
- 배송 지연 건수

---

## 10. MVP Scope 확정 (최종)

#### 반드시 구현

- 사용자 RBAC
- 상품/카테고리
- 창고/Location
- Lot 재고
- Reservation
- StockMovement
- 주문
- 출고
- 배송
- 알림

#### 제외

- IoT 온도 연동
- 반품/환불
- 결제
- 외부 API 연동
- 정산
---
# 정책


## 도메인 설계 결정
### Product

#### SKU 생성 정책
형식: `FX-{CATEGORY}-{NNNN}`
예: `FX-MLK-0001`, `FX-FSH-0123`
중복 기준: SKU UNIQUE
생성 주체: ADMIN(등록 시 생성)

#### 보관 타입 Enum
`ROOM`, `REFRIGERATED`, `FROZEN`
#### 유통기한 기본값
정책상품에 default_shelf_life_days 저장(예: 우유 10일)
실제 LOT는 입고 시 expired_at를 확정(기본값을 초기값으로 제안 가능)

#### 상품 상태 Enum
`ACTIVE`, `INACTIVE`

### Warehouse
#### 창고 타입
`COLD_ONLY`, `FROZEN_ONLY`, `MIXED`
배송 권역 매핑 방식

Address → Zone(권역) → Warehouse
MVP에서는 간단히:
`zone_code`(예: `SEOUL_GANGNAM`)를 주문 주소에서 결정(룰 기반)
`warehouse_zone` 테이블로 zone과 창고를 매핑

#### 컷오프 정책
D+0 출고 컷오프: 매일 15:00
15:00 이전 결제 완료(PAID) 주문 → 당일 출고 대상
이후 주문 → 익일 출고 대상


### Inventory

재고 관리 단위
상품 + 창고 + LOT 단위(필수)

LOT 번호 생성 규칙
`LOT-{SKU}-{YYYYMMDD}-{SEQ}`
예: `LOT-FX-MLK-0001-20260211-001`

출고 정책
FEFO 강제(expired_at 빠른 LOT부터 차감)

유통기한 초과 처리
`expired_at < today` 인 LOT는 출고 할당 불가
운영자가 폐기 처리 시 `DISCARDED` 로 전환(수량 0 또는 별도 discarded_qty 기록)

재고 부족 처리
MVP: 주문 생성 단계에서 차단(전체 거절)
(차후) 대체 창고 탐색/부분 출고 확장 가능


### Order

주문 생성 조건
상품 상태 ACTIVE
주문 요청 수량 ≤ 가용 재고(유통기한 유효 LOT 합)
권역(zone)로 매핑된 창고가 존재
컷오프 기준에 따라 출고 예정일 산정 가능

주문 상태 Enum
`CREATED`, `PAID`, `PREPARING`, `SHIPPED`, `DELIVERED`, `CANCELED`

상태 전이 규칙
정상: `CREATED → PAID → PREPARING → SHIPPED → DELIVERED`
취소: `CREATED/PAID`에서만 `CANCELED` 가능
`PREPARING`부터 취소 불가(MVP 단순화)

취소 가능 시점
PAID까지 가능, PREPARING부터 불가

부분 취소/부분 출고

## Shipment

배송 타입
`REFRIGERATED`, `FROZEN`

    혼합 주문(MIX): MVP에서는 분리 배송 2건 생성
        냉장 상품 Shipment
        냉동 상품 Shipment

배송 상태 Enum
`READY`, `IN_TRANSIT`, `DELIVERED`, `FAILED`

배송 실패 처리
MVP: `FAILED` 시 주문은 `SHIPPED` 유지 + 배송 실패 사유 저장
(차후) 재배송/환불/폐기 정책 추가

배송 지연 기준
`IN_TRANSIT` 후 24시간 초과 → `DELAYED_FLAG = true`(품질 주의 플래그)

---

## 핵심 비즈니스  정책

보관 조건 불일치 시 출고 차단
냉동 상품은 `FROZEN` 가능한 창고/배송만 허용

유통기한 초과 출고 금지
expired LOT 차단

재고 0 이하 차감 금지
출고 할당 시점에 가용 수량 검증 + DB 레벨 락

SHIPPED 이후 취소 불가
MVP: PREPARING부터 취소 불가로 더 강하게 설정

임박 재고 기준
`expired_at today <= 2일` → 임박 경고 대상으로 분류(조회 API에서 필터 제공)
---
## 테스트 정책
- 정상 플로우
    - LOT 입고(우유 20개, exp 2/18)
    - 주문 3개 생성
    - 출고 생성 → FEFO 할당 → 확정
    - 배송 생성 → 배송 완료
- 재료 부족
    - 가용 2개인데 3개 주문 → 주문 생성 차단
- 유통기한 초과
    - exp 지난 LOT 존재 → 출고 할당에서 제외, 전량 expired면 주문 차단
- 권역 불가
    - 주문 주소 zone에 매핑된 창고 없음 → 주문 차단
- 동시 주문 충돌
    - 같은 LOT에 대해 동시 차감 발생 시 **한쪽은 실패**(락/버전으로 방지)
---
## 개발 정책
- 패키지 구조 = 도메인 구조
- 공통 예외 : common.exception에 코드/메세지 표준화
- enum
    - productStatus, StorageType
    - WarehouseType
    - LotStatus(ACTIVE, DISCARDED)
    - OrderStatus
    - ShipmentStatus
- 테스트 전략
    - 도메인 규칙 : 단위 테스트(junit)
    - 상태 전이/락 : 통합 테스트

---
