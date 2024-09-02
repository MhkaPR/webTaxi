package ir.mhkapr.webtaxi.service;

import ir.mhkapr.webtaxi.DTOs.DriverDistanceProjection;
import ir.mhkapr.webtaxi.configuration.OrderTypeToVehicleTypeMapper;
import ir.mhkapr.webtaxi.entity.Driver;
import ir.mhkapr.webtaxi.entity.Order;
import ir.mhkapr.webtaxi.entity.User;
import ir.mhkapr.webtaxi.entity.enums.OrderType;
import ir.mhkapr.webtaxi.entity.enums.UserStatus;
import ir.mhkapr.webtaxi.exception.DriverNotFoundInRangeException;
import ir.mhkapr.webtaxi.repository.DriverRepository;
import ir.mhkapr.webtaxi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DriverFinderService {
    private final DriverRepository driverRepository;
    private final UserRepository userRepository;
    private final OrderTypeToVehicleTypeMapper orderTypeToVehicleTypeMapper;
    public Driver findDriver(Point origin , Double tolerance , OrderType orderType) throws DriverNotFoundInRangeException {
        Optional<DriverDistanceProjection> foundDriver = driverRepository.findDriversFromPoint(origin);
        if(foundDriver.isPresent()){
            Driver driver = driverRepository.findById(foundDriver.get().getDriverId()).orElseThrow();
            if(foundDriver.get().getDistance() <= tolerance && isSuitableDriver(driver,orderType)) {
                return driver;
            }
        }
        throw new DriverNotFoundInRangeException();
    }
    private Boolean isSuitableDriver(Driver driver, OrderType orderType){
        return driver.getUser().getStatus()  == UserStatus.INACTIVE
                && driver.getVehicle().getVehicleType() == orderTypeToVehicleTypeMapper.getMap().get(orderType);
    }
}
