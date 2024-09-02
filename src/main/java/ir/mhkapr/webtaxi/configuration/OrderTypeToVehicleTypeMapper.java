package ir.mhkapr.webtaxi.configuration;

import ir.mhkapr.webtaxi.entity.enums.OrderType;
import ir.mhkapr.webtaxi.entity.enums.VehicleType;
import jakarta.annotation.PostConstruct;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Getter
public class OrderTypeToVehicleTypeMapper {
    private final Map<OrderType, VehicleType> map = new HashMap<>();
    @PostConstruct
    private void buildMap(){
        map.put(OrderType.CLASSIC_CAR,VehicleType.CAR_CLASSIC);
        map.put(OrderType.LUX_CAR,VehicleType.CAR_LUX);
        map.put(OrderType.TRACK,VehicleType.TRACK);
    }

}
