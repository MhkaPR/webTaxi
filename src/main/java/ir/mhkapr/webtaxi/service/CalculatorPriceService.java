package ir.mhkapr.webtaxi.service;

import ir.mhkapr.webtaxi.entity.enums.OrderType;
import ir.mhkapr.webtaxi.repository.LocationRepository;
import ir.mhkapr.webtaxi.service.priceClasses.CalculatorPrice;
import ir.mhkapr.webtaxi.service.priceClasses.Classic_Car_PriceCalculator;
import ir.mhkapr.webtaxi.service.priceClasses.Lux_Car_PriceCalculator;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class CalculatorPriceService {
    private final LocationRepository locationRepository;
    private HashMap<OrderType, CalculatorPrice> typeConvertor;

    @PostConstruct
    private void init(){
       typeConvertor = new HashMap<>();
       typeConvertor.put(OrderType.CLASSIC_CAR, new Classic_Car_PriceCalculator());
       typeConvertor.put(OrderType.LUX_CAR, new Lux_Car_PriceCalculator());
    }
    public Double calculatePrice(Point origin, Point destination, OrderType orderType){
        Double distance = locationRepository.calculateDistance(origin,destination);
        return typeConvertor.get(orderType).calculatePrice(distance);
    }

}
