package ir.mhkapr.webtaxi;

import ir.mhkapr.webtaxi.DTOs.DriverDistanceProjection;
import ir.mhkapr.webtaxi.entity.Driver;
import ir.mhkapr.webtaxi.exception.DriverNotFoundInRangeException;
import ir.mhkapr.webtaxi.repository.DriverRepository;
import ir.mhkapr.webtaxi.service.DriverFinderService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.mockito.Mockito.*;
@SpringBootTest
@Transactional
public class DriverFinderTest {

    @Autowired
    private  DriverRepository driverRepository;
    @Test
    void TestFindingByRepositoryDrivers(){
        GeometryFactory geometryFactory = new GeometryFactory();
        Point point = geometryFactory.createPoint(new Coordinate(59.655724,36.273983));

        Optional<DriverDistanceProjection> driver = driverRepository.findDriversFromPoint(point);

        assertTrue(driver.isPresent());
        System.out.println(driver.get().getDistance());
    }
}
