package ir.mhkapr.webtaxi.service;

import com.fasterxml.jackson.databind.ser.std.StdArraySerializers;
import ir.mhkapr.webtaxi.DTOs.DriverAuthenticationResponse;
import ir.mhkapr.webtaxi.DTOs.DriverRegisterRequest;
import ir.mhkapr.webtaxi.entity.Driver;
import ir.mhkapr.webtaxi.entity.User;
import ir.mhkapr.webtaxi.entity.Vehicle;
import ir.mhkapr.webtaxi.entity.enums.Role;
import ir.mhkapr.webtaxi.exception.DriverAlreadyExistsException;
import ir.mhkapr.webtaxi.exception.UserAlreadyExistsException;
import ir.mhkapr.webtaxi.exception.UserNotFoundException;
import ir.mhkapr.webtaxi.mapper.UserUserInfoDTOMapper;
import ir.mhkapr.webtaxi.mapper.VehicleVehicleInfoDTOMapper;
import ir.mhkapr.webtaxi.repository.DriverRepository;
import ir.mhkapr.webtaxi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DriverService {
    private final DriverRepository driverRepository;
    private final UserRepository userRepository;
    public DriverAuthenticationResponse register(DriverRegisterRequest request)
            throws UserAlreadyExistsException, UserNotFoundException, DriverAlreadyExistsException {

        Point driverLocation = createPoint(request.getLocation().getLongitude(), request.getLocation().getLatitude());

        User userOfDriver =  userRepository.findByPhoneNumber(request.getPhoneNumber())
                .orElseThrow(UserNotFoundException::new);

        if(existsDriverByUserId(userOfDriver.getUserId())) throw new DriverAlreadyExistsException();

        Driver newDriver = Driver.builder()
                .user(userOfDriver)
                .location(driverLocation)
                .vehicle(VehicleVehicleInfoDTOMapper.INSTANCE.VehicleInfoDTOToVehicle(request.getVehicleInfo()))
                .build();
        newDriver = driverRepository.save(newDriver);

        userOfDriver.setRole(Role.DRIVER);
        userRepository.save(userOfDriver);

        return DriverAuthenticationResponse.builder()
                .userInfo(UserUserInfoDTOMapper.INSTANCE.UserToUserInfoDTO(newDriver.getUser()))
                .vehicleInfo(VehicleVehicleInfoDTOMapper.INSTANCE.VehicleToVehicleInfoDTO(newDriver.getVehicle()))
                .build();
    }
    private Point createPoint(Double longitude ,Double latitude){
        GeometryFactory geometryFactory = new GeometryFactory();
        return geometryFactory.createPoint(new Coordinate(longitude,latitude));
    }
    private Boolean existsDriverByUserId(Long userId){
       Optional<Driver> driverOptional = driverRepository.findDriverByUserId(userId);
       return driverOptional.isPresent();
    }
}
