package ir.mhkapr.webtaxi.service;

import ir.mhkapr.webtaxi.entity.enums.OrderType;
import ir.mhkapr.webtaxi.repository.LocationRepository;
import ir.mhkapr.webtaxi.service.priceClasses.CalculatorPrice;
import lombok.RequiredArgsConstructor;

import org.locationtech.jts.geom.Point;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PriceCalculatorService {
    private final LocationRepository locationRepository;
    private final ApplicationContext applicationContext;

    public Double calculatePrice(Point origin, Point destination, OrderType orderType){
        Double distance = locationRepository.calculateDistance(origin,destination);
        return ((CalculatorPrice)applicationContext.getBean(orderType.name())).calculatePrice(distance);
    }

}
