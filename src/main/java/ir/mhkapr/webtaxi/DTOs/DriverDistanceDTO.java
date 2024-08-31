package ir.mhkapr.webtaxi.DTOs;

import ir.mhkapr.webtaxi.entity.Driver;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DriverDistanceDTO {
    Driver driver;
    Double distance;
}
