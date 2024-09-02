package ir.mhkapr.webtaxi.DTOs.notice;

import ir.mhkapr.webtaxi.DTOs.LocationDTO;
import ir.mhkapr.webtaxi.DTOs.UserInfoDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticeDriverNewTripRequestDTO {
    UserInfoDTO customerInfo;
    Double price;
    LocationDTO origin;
    LocationDTO destination;
}
