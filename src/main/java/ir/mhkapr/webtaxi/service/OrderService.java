package ir.mhkapr.webtaxi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import ir.mhkapr.webtaxi.DTOs.*;
import ir.mhkapr.webtaxi.entity.Driver;
import ir.mhkapr.webtaxi.entity.Order;
import ir.mhkapr.webtaxi.entity.User;
import ir.mhkapr.webtaxi.entity.enums.OrderStatus;
import ir.mhkapr.webtaxi.entity.enums.Role;
import ir.mhkapr.webtaxi.entity.enums.UserStatus;
import ir.mhkapr.webtaxi.exception.DriverNotFoundInRangeException;
import ir.mhkapr.webtaxi.exception.UserBusynessException;
import ir.mhkapr.webtaxi.mapper.UserUserInfoDTOMapper;
import ir.mhkapr.webtaxi.repository.OrderRepository;
import ir.mhkapr.webtaxi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.boot.logging.LogLevel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final Double TOLERANCE = 500.0;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final DriverFinderService driverFinderService;
    private final PriceCalculatorService priceCalculatorService;
    private final PublisherService publisher;
    private Boolean isFreeCustomer(User user){
        return user.getStatus() == UserStatus.INACTIVE && user.getRole() != Role.DRIVER;
    }
    public OrderResponse registerOrder(OrderRequest request)
            throws UserBusynessException, DriverNotFoundInRangeException, JsonProcessingException {

        String phoneNumber = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByPhoneNumber(phoneNumber).orElseThrow();

        if(!isFreeCustomer(user)) throw new UserBusynessException();

        Point originPoint = createPoint(request.getOrigin());
        Point destinationPoint = createPoint(request.getDestination());

        Driver foundDriver = driverFinderService.findDriver(originPoint,TOLERANCE,request.getOrderType());
        setUserStatus(foundDriver.getUser(),UserStatus.DRIVING);

        Double price = priceCalculatorService.calculatePrice(originPoint,destinationPoint,request.getOrderType());

        user = setUserStatus(user,UserStatus.PENDING_PAYMENT);

        Order newOrder = Order.builder()
                .price(price)
                .origin(originPoint)
                .destination(destinationPoint)
                .type(request.getOrderType())
                .createdAt(new Date())
                .driverId(foundDriver.getDriver_id())
                .status(OrderStatus.PENDING)
                .userId(user.getUserId())
                .build();
        orderRepository.save(newOrder);

        DriverInfoDTO tempDriverInfoDTO = DriverInfoDTO.builder()
                .vehicleType(foundDriver.getVehicle().getVehicleType())
                .licencePlate(foundDriver.getVehicle().getLicencePlate())
                .baseInformation(UserUserInfoDTOMapper.INSTANCE.UserToUserInfoDTO(foundDriver.getUser()))
                .build();

        publisher.noticeDriverNewTrip(UserUserInfoDTOMapper.INSTANCE.UserToUserInfoDTO(user)
                ,price,request.getOrigin() , request.getDestination());

        OrderResponse orderResponse = OrderResponse.builder()
                .price(newOrder.getPrice())
                .origin(LocationDTO.builder()
                        .longitude(newOrder.getOrigin().getX())
                        .latitude(newOrder.getOrigin().getY())
                        .build())
                .destination(LocationDTO.builder()
                        .longitude(newOrder.getDestination().getX())
                        .latitude(newOrder.getDestination().getY())
                        .build())
                .type(newOrder.getType())
                .driverInfo(tempDriverInfoDTO)
                .build();
        publisher.noticeLog(LogLevel.INFO,"a new order registered" ,
                "webTaxi.root.service.OrderService:registerOrder",new Date() , orderResponse);
        return orderResponse;
    }
    private Point createPoint(LocationDTO locationDTO){
        GeometryFactory geometryFactory = new GeometryFactory();
        return geometryFactory.createPoint(new Coordinate(locationDTO.getLongitude(), locationDTO.getLatitude()));
    }
    private User setUserStatus(User user,UserStatus userStatus){
        user.setStatus(userStatus);
        return userRepository.save(user);
    }
}
