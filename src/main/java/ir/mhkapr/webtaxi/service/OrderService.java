package ir.mhkapr.webtaxi.service;

import ir.mhkapr.webtaxi.entity.User;
import ir.mhkapr.webtaxi.entity.enums.Role;
import ir.mhkapr.webtaxi.entity.enums.UserStatus;
import ir.mhkapr.webtaxi.repository.OrderRepository;
import ir.mhkapr.webtaxi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private Boolean isFreeCustomer(User user){
        return user.getStatus() == UserStatus.INACTIVE && user.getRole() != Role.DRIVER;
    }

}
