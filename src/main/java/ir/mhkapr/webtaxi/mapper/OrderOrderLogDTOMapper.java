package ir.mhkapr.webtaxi.mapper;

import ir.mhkapr.webtaxi.DTOs.LocationDTO;
import ir.mhkapr.webtaxi.DTOs.OrderLogDTO;
import ir.mhkapr.webtaxi.DTOs.UserInfoDTO;
import ir.mhkapr.webtaxi.entity.Order;
import ir.mhkapr.webtaxi.entity.User;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderOrderLogDTOMapper {
    OrderOrderLogDTOMapper INSTANCE = Mappers.getMapper(OrderOrderLogDTOMapper.class);

    @Mapping(source = "origin", target = "origin", qualifiedByName = "pointToLocationDTO")
    @Mapping(source = "destination", target = "destination", qualifiedByName = "pointToLocationDTO")
    OrderLogDTO toOrderLogDTO(Order order);

    @Mapping(source = "origin", target = "origin", qualifiedByName = "locationDTOToPoint")
    @Mapping(source = "destination", target = "destination", qualifiedByName = "locationDTOToPoint")
    Order toOrder(OrderLogDTO orderLogDTO);

    @Named("pointToLocationDTO")
    default LocationDTO pointToLocationDTO(Point point) {
        if (point == null) {
            return null;
        }
        return new LocationDTO(point.getX(), point.getY());
    }

    @Named("locationDTOToPoint")
    default Point locationDTOToPoint(LocationDTO locationDTO) {
        GeometryFactory geometryFactory = new GeometryFactory();
        if (locationDTO == null) {
            return null;
        }
        return geometryFactory.createPoint(new Coordinate(locationDTO.getLongitude(), locationDTO.getLatitude()));
    }
}
