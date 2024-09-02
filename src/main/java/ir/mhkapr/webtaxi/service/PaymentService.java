package ir.mhkapr.webtaxi.service;

import ir.mhkapr.webtaxi.DTOs.PaymentResponse;
import ir.mhkapr.webtaxi.entity.Order;
import ir.mhkapr.webtaxi.entity.User;
import ir.mhkapr.webtaxi.entity.enums.OrderStatus;
import ir.mhkapr.webtaxi.entity.enums.UserStatus;
import ir.mhkapr.webtaxi.exception.UserBusynessException;
import ir.mhkapr.webtaxi.repository.OrderRepository;
import ir.mhkapr.webtaxi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    public PaymentResponse pay() throws UserBusynessException {
        String phoneNumber = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByPhoneNumber(phoneNumber).orElseThrow();
        if(!hasUserPendingStatus(user)) throw new UserBusynessException();
        user.setStatus(UserStatus.PAID);
        user = userRepository.save(user);

        Order order = orderRepository.findPendingOrderByUserId(user.getUserId()).orElseThrow();
        order.setStatus(OrderStatus.PAID);
        order.setFinishedAt(new Date());
        order = orderRepository.save(order);

        return PaymentResponse.builder()
                .pricePaid(order.getPrice())
                .time(order.getFinishedAt())
                .build();
    }
     private Boolean hasUserPendingStatus(User user){
        return user.getStatus() == UserStatus.PENDING_PAYMENT;
    }
}