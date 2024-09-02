package ir.mhkapr.webtaxi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ir.mhkapr.webtaxi.DTOs.NoticeDriverRequestDTO;
import ir.mhkapr.webtaxi.DTOs.UserInfoDTO;
import ir.mhkapr.webtaxi.entity.User;
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
    public void publishNoticeDriver(UserInfoDTO customerInfo, Double price) throws JsonProcessingException {
        NoticeDriverRequestDTO request = NoticeDriverRequestDTO.builder()
                .customerInfo(customerInfo)
                .price(price)
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        byte[] byteMessage = objectMapper.writeValueAsBytes(request);
        rabbitTemplate.convertAndSend(exchange,routing_key,byteMessage);
    }
}
