package ir.mhkapr.webtaxi.service;

import ir.mhkapr.webtaxi.DTOs.DriverInfoDTO;
import ir.mhkapr.webtaxi.DTOs.LocationDTO;
import ir.mhkapr.webtaxi.DTOs.OrderResponse;
import ir.mhkapr.webtaxi.DTOs.StatusResponse;
import ir.mhkapr.webtaxi.entity.Driver;
import ir.mhkapr.webtaxi.entity.Order;
import ir.mhkapr.webtaxi.entity.User;
import ir.mhkapr.webtaxi.entity.enums.UserStatus;
import ir.mhkapr.webtaxi.mapper.UserUserInfoDTOMapper;
import ir.mhkapr.webtaxi.repository.DriverRepository;
import ir.mhkapr.webtaxi.repository.OrderRepository;
import ir.mhkapr.webtaxi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
            switch (user.getStatus()) {
                case PENDING_PAYMENT -> {
                    Order order = orderRepository.findPendingOrderByUserId(user.getUserId()).orElseThrow();
                    OrderResponse orderResponse = createOrderResponse(order);
                    info.put("message", "you should pay price!");
                    info.put("orderInformation", orderResponse);
                }
                case PAID -> {
                    Order order = orderRepository.findPaidOrderByUserId(user.getUserId()).orElseThrow();
                    OrderResponse orderResponse = createOrderResponse(order);
                    info.put("message", "trip expenses have been paid!");
                    info.put("orderInformation", orderResponse);
                }
                case DRIVING -> {
                    Driver driver = driverRepository.findDriverByUserId(user.getUserId()).orElseThrow();
                    Order order = orderRepository.findPendingOrderByDriverId(driver.getDriver_id())
                            .or(() -> orderRepository.findPaidOrderByDriverId(driver.getDriver_id())).orElseThrow();
                    OrderResponse orderResponse = createOrderResponse(order);
                    info.put("message", "you are driving");
                    info.put("orderInformation", orderResponse);
                    User customer = userRepository.findById(order.getUserId()).orElseThrow();
                    info.put("customerInfo" , UserUserInfoDTOMapper.INSTANCE.UserToUserInfoDTO(customer));
                    info.put("orderStatus" , order.getStatus());
                }
            }
        }
        return info;
    }
    private OrderResponse createOrderResponse(Order order){
        LocationDTO origin = LocationDTO.builder()
                .longitude(order.getOrigin().getX())
                .latitude(order.getOrigin().getY())
                .build();
        LocationDTO destination = LocationDTO.builder()
                .longitude(order.getDestination().getX())
                .latitude(order.getDestination().getY())
                .build();

        Driver driver = driverRepository.findById(order.getDriverId()).orElseThrow();
        DriverInfoDTO driverInfo = DriverInfoDTO.builder()
                .baseInformation(UserUserInfoDTOMapper.INSTANCE.UserToUserInfoDTO(driver.getUser()))
                .licencePlate(driver.getVehicle().getLicencePlate())
                .vehicleType(driver.getVehicle().getVehicleType())
                .build();

        return OrderResponse.builder()
                .driverInfo(driverInfo)
                .price(order.getPrice())
                .type(order.getType())
                .origin(origin)
                .destination(destination)
                .build();
    }
}
