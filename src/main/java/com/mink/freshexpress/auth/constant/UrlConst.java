package com.mink.freshexpress.auth.constant;
//todo : url 상수화 리팩터링
public class UrlConst {

    //로그인 필터 화이트 리스트
    public static final String[] WHITE_LIST = {"/", "/auth/signin", "/auth/login"};
    public static final String[] ADMIN_URL = {"/**"};
    public static final String[] COSTOMER_URL = {"/user/**"};

}
