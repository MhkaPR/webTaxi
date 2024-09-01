package ir.mhkapr.webtaxi.DTOs;

import ir.mhkapr.webtaxi.entity.enums.OrderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    LocationDTO origin;
    LocationDTO destination;
    OrderType type;
    Double price;
    DriverInfoDTO driverInfo;
}
