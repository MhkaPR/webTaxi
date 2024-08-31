package ir.mhkapr.webtaxi.service;

import ir.mhkapr.webtaxi.DTOs.DriverDistanceProjection;
import ir.mhkapr.webtaxi.entity.Driver;
import ir.mhkapr.webtaxi.exception.DriverNotFoundInRangeException;
import ir.mhkapr.webtaxi.repository.DriverRepository;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DriverFinderService {
    private final DriverRepository driverRepository;
    public Driver findDriver(Point origin , Double tolerance) throws DriverNotFoundInRangeException {
        Optional<DriverDistanceProjection> foundDriver = driverRepository.findDriversFromPoint(origin);
        if(foundDriver.isPresent()){
            if(foundDriver.get().getDistance() <= tolerance) {
                return foundDriver.get().getDriver();
            }
        }
        throw new DriverNotFoundInRangeException();
    }
}
