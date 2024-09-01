package ir.mhkapr.webtaxi.service;

import com.sun.jdi.event.StepEvent;
import ir.mhkapr.webtaxi.DTOs.DriverInfoDTO;
import ir.mhkapr.webtaxi.DTOs.LocationDTO;
import ir.mhkapr.webtaxi.DTOs.OrderResponse;
import ir.mhkapr.webtaxi.DTOs.StatusResponse;
import ir.mhkapr.webtaxi.entity.Driver;
import ir.mhkapr.webtaxi.entity.Order;
import ir.mhkapr.webtaxi.entity.User;
import ir.mhkapr.webtaxi.entity.enums.UserStatus;
import ir.mhkapr.webtaxi.mapper.PointLocationDTOMapper;
import ir.mhkapr.webtaxi.mapper.UserUserInfoDTOMapper;
import ir.mhkapr.webtaxi.repository.DriverRepository;
import ir.mhkapr.webtaxi.repository.OrderRepository;
import ir.mhkapr.webtaxi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.bridge.IMessage;
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
        return StatusResponse.builder()
                .info(buildStatusResponse(user))
                .build();
    }
    private Map<String,Object> buildStatusResponse(User user){
        Map<String,Object> info =new HashMap<>();
        if(user.getStatus() == UserStatus.INACTIVE) info.put("message","you have not any trip!");
        else {
            Order order = orderRepository.findPendingOrderByUserId(user.getUserId()).orElseThrow();

            LocationDTO origin = PointLocationDTOMapper.INSTANCE.pointToLocationDTO(order.getOrigin());
            LocationDTO destination = PointLocationDTOMapper.INSTANCE.pointToLocationDTO(order.getDestination());

            Driver driver = driverRepository.findById(order.getDriverId()).orElseThrow();
            DriverInfoDTO driverInfo = DriverInfoDTO.builder()
                    .baseInformation(UserUserInfoDTOMapper.INSTANCE.UserToUserInfoDTO(user))
                    .licencePlate(driver.getVehicle().getLicencePlate())
                    .vehicleType(driver.getVehicle().getVehicleType())
                    .build();

            OrderResponse orderResponse = OrderResponse.builder()
                    .driverInfo(driverInfo)
                    .price(order.getPrice())
                    .type(order.getType())
                    .origin(origin)
                    .destination(destination)
                    .build();

            switch (user.getStatus()) {
                case PENDING_PAYMENT -> {
                    info.put("message", "you should pay price!");
                    info.put("orderInformation", orderResponse);
                }
                case PAID -> {
                    info.put("message", "trip expenses have been paid!");
                    info.put("orderInformation", orderResponse);
                }
                case DRIVING -> {
                    info.put("message", "you are driving");
                    info.put("orderInformation", orderResponse);
                    User customer = userRepository.findById(order.getUserId()).orElseThrow();
                    info.put("customerInfo" , UserUserInfoDTOMapper.INSTANCE.UserToUserInfoDTO(customer));
                }

            }
        }
        return info;
    }
}
