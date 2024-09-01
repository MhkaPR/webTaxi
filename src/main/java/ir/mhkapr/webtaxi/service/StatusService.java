package ir.mhkapr.webtaxi.service;

import com.sun.jdi.event.StepEvent;
import ir.mhkapr.webtaxi.DTOs.DriverInfoDTO;
import ir.mhkapr.webtaxi.DTOs.OrderResponse;
import ir.mhkapr.webtaxi.DTOs.StatusResponse;
import ir.mhkapr.webtaxi.entity.Order;
import ir.mhkapr.webtaxi.entity.User;
import ir.mhkapr.webtaxi.entity.enums.UserStatus;
import ir.mhkapr.webtaxi.repository.DriverRepository;
import ir.mhkapr.webtaxi.repository.OrderRepository;
import ir.mhkapr.webtaxi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatusService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final DriverRepository driverRepository;
    public StatusResponse getStatus(){
        String phoneNumber = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByPhoneNumber(phoneNumber).orElseThrow();

    }
    private Map<String,Object> buildStatusResponse(User user){
        Map<String,Object> info =new HashMap<>();
        switch (user.getStatus()){
            case INACTIVE -> info.put("message","you have not any trip!");
            case PENDING_PAYMENT -> {

            }
        }
    }
}
