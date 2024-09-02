package ir.mhkapr.webtaxi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ir.mhkapr.webtaxi.DTOs.LocationDTO;
import ir.mhkapr.webtaxi.DTOs.PaymentResponse;
import ir.mhkapr.webtaxi.DTOs.notice.NoticeDriverNewTripRequestDTO;
import ir.mhkapr.webtaxi.DTOs.UserInfoDTO;
import ir.mhkapr.webtaxi.DTOs.notice.NoticeDriverPaidPriceDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@Service
@RequiredArgsConstructor
public class PublisherService {
    private final RabbitTemplate rabbitTemplate;
    @Value("${rabbitmq.notice-driver-routing-key}")
    String  new_trip_routing_key;
    @Value("${rabbitmq.notice-driver-paid-price-routing-key}")
    String  paid_price_routing_key;
    @Value("${rabbitmq.exchange}")
    String  exchange;
    public void noticeDriverNewTrip(UserInfoDTO customerInfo, Double price, LocationDTO origin , LocationDTO destination) throws JsonProcessingException {
        NoticeDriverNewTripRequestDTO request = NoticeDriverNewTripRequestDTO.builder()
                .customerInfo(customerInfo)
                .price(price)
                .origin(origin)
                .destination(destination)
                .build();
        sendData(request,new_trip_routing_key);
    }
    public void noticeDriverPaidPrice(PaymentResponse paymentInfo) throws JsonProcessingException {
        NoticeDriverPaidPriceDTO request = NoticeDriverPaidPriceDTO.builder()
                .paymentInfo(paymentInfo)
                .message("do not get cash from customer!")
                .build();
        sendData(request,paid_price_routing_key);
    }

    private void sendData(Object data,String routingKey) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        byte[] byteMessage = objectMapper.writeValueAsBytes(data);
        rabbitTemplate.convertAndSend(exchange,routingKey,byteMessage);
    }
}
