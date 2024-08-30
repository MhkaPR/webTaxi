package ir.mhkapr.webtaxi.DTOs;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DriverRegisterRequest {
    LocationDTO location;
    VehicleInfoDTO vehicleInfo;
}
