package ir.mhkapr.webtaxi.mapper;

import ir.mhkapr.webtaxi.DTOs.OrderLogDTO;
import ir.mhkapr.webtaxi.DTOs.UserInfoDTO;
import ir.mhkapr.webtaxi.entity.Order;
import ir.mhkapr.webtaxi.entity.User;
import org.mapstruct.factory.Mappers;

public interface OrderOrderLogDTOMapper {
    OrderOrderLogDTOMapper INSTANCE = Mappers.getMapper(OrderOrderLogDTOMapper.class);

    OrderLogDTO OrderToOrderLogDTO(Order order);

    Order OrderLogDTOToOrder(OrderLogDTO orderLogDTO);
}
