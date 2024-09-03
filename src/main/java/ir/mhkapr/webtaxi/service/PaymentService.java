package ir.mhkapr.webtaxi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import ir.mhkapr.webtaxi.DTOs.PaymentResponse;
import ir.mhkapr.webtaxi.entity.Order;
import ir.mhkapr.webtaxi.entity.User;
import ir.mhkapr.webtaxi.entity.enums.OrderStatus;
import ir.mhkapr.webtaxi.entity.enums.UserStatus;
import ir.mhkapr.webtaxi.exception.UserBusynessException;
import ir.mhkapr.webtaxi.repository.OrderRepository;
import ir.mhkapr.webtaxi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.logging.LogLevel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final PublisherService publisher;
    public PaymentResponse pay() throws UserBusynessException, JsonProcessingException {
        String phoneNumber = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByPhoneNumber(phoneNumber).orElseThrow();
        if(!hasUserPendingStatus(user)) throw new UserBusynessException();
        user.setStatus(UserStatus.PAID);
        user = userRepository.save(user);

        Order order = orderRepository.findPendingOrderByUserId(user.getUserId()).orElseThrow();
        order.setStatus(OrderStatus.PAID);
        order.setFinishedAt(new Date());
        order = orderRepository.save(order);
        PaymentResponse response = PaymentResponse.builder()
                .pricePaid(order.getPrice())
                .time(order.getFinishedAt())
                .build();
        publisher.noticeDriverPaidPrice(response);

        publisher.noticeLog(LogLevel.INFO,"customer paid and was registered in database" ,
                "webTaxi.root.service.StatusService:getStatus",new Date() , order);

        return response;
    }
     private Boolean hasUserPendingStatus(User user){
        return user.getStatus() == UserStatus.PENDING_PAYMENT;
    }
}
