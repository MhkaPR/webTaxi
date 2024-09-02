package ir.mhkapr.webtaxi.DTOs;

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
