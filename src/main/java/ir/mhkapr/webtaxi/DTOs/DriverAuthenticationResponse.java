package ir.mhkapr.webtaxi.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DriverAuthenticationResponse {
    UserInfoDTO userInfo;
    VehicleInfoDTO vehicleInfo;
}
