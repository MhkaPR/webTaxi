package ir.mhkapr.webtaxi.DTOs.notice;

import ir.mhkapr.webtaxi.DTOs.PaymentResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticeDriverPaidPriceDTO {
    String message;
    PaymentResponse paymentInfo;
}
