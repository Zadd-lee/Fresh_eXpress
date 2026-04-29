package com.mink.freshexpress.delivery.service.imp;

import com.mink.freshexpress.common.exception.constant.CommonErrorCode;
import com.mink.freshexpress.common.exception.constant.OrderErrorCode;
import com.mink.freshexpress.common.util.Validator;
import com.mink.freshexpress.delivery.model.Delivery;
import com.mink.freshexpress.delivery.repository.DeliveryRepository;
import com.mink.freshexpress.delivery.service.DeliveryService;
import com.mink.freshexpress.order.model.Order;
import com.mink.freshexpress.order.repository.OrderRepository;
import com.mink.freshexpress.user.model.User;
import com.mink.freshexpress.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.mink.freshexpress.common.util.Validator.*;

@RequiredArgsConstructor
@Service
public class DeliveryServiceImpl implements DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public void create(String username, Long orderId) {

        System.out.println("DeliveryServiceImpl.create");
        //valid
        Order order = valid(orderRepository.findById(orderId), OrderErrorCode.NOT_FOUND);
        User deliveryMan = valid(userRepository.findByEmail(username), CommonErrorCode.INTERNAL_SERVER_ERROR);

        LocalDateTime nowDateTime = LocalDateTime.now();
        Delivery delivery = new Delivery(nowDateTime, order);

        deliveryRepository.save(delivery);
    }
}
