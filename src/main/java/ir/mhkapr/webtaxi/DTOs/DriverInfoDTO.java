package ir.mhkapr.webtaxi.DTOs;

import ir.mhkapr.webtaxi.entity.enums.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DriverInfoDTO {
    UserInfoDTO baseInformation;
    String licencePlate;
    VehicleType vehicleType;
}
