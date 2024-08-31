package ir.mhkapr.webtaxi.DTOs;

import ir.mhkapr.webtaxi.entity.User;
import ir.mhkapr.webtaxi.entity.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DriverInfoDTO {

    User user;
    LocationDTO location;
    Vehicle vehicle;
}
