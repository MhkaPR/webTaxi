package ir.mhkapr.webtaxi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ir.mhkapr.webtaxi.DTOs.LocationDTO;
import ir.mhkapr.webtaxi.DTOs.NoticeDriverNewTripRequestDTO;
import ir.mhkapr.webtaxi.DTOs.UserInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@Service
@RequiredArgsConstructor
public class Publisher {
    private final RabbitTemplate rabbitTemplate;
    @Value("${rabbitmq.notice-driver-routing-key}")
    String  routing_key;
    @Value("${rabbitmq.exchange}")
    String  exchange;
    public void noticeDriverNewTrip(UserInfoDTO customerInfo, Double price, LocationDTO origin , LocationDTO destination) throws JsonProcessingException {
        NoticeDriverNewTripRequestDTO request = NoticeDriverNewTripRequestDTO.builder()
                .customerInfo(customerInfo)
                .price(price)
                .origin(origin)
                .destination(destination)
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        byte[] byteMessage = objectMapper.writeValueAsBytes(request);
        rabbitTemplate.convertAndSend(exchange,routing_key,byteMessage);
    }
}
