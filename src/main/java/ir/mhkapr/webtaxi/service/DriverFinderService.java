package ir.mhkapr.webtaxi.service;

import ir.mhkapr.webtaxi.DTOs.DriverDistanceDTO;
import ir.mhkapr.webtaxi.DTOs.DriverInfoDTO;
import ir.mhkapr.webtaxi.entity.Driver;
import ir.mhkapr.webtaxi.repository.DriverRepository;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DriverFinderService {
    private final DriverRepository driverRepository;
    public DriverInfoDTO findDriver(Point origin , Double tolerance){
        Optional<DriverDistanceDTO> foundDriver = driverRepository.findDriversFromPoint(origin);
        if(foundDriver.isPresent()){
            if(foundDriver.get().getDistance() <= tolerance){

            }
        }
    }
}
