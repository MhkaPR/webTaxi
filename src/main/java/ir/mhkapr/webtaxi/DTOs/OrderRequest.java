package ir.mhkapr.webtaxi.DTOs;

import ir.mhkapr.webtaxi.entity.enums.OrderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    LocationDTO origin;
    LocationDTO destination;
    OrderType orderType;
}
