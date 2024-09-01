package ir.mhkapr.webtaxi.mapper;

import ir.mhkapr.webtaxi.DTOs.LocationDTO;
import org.locationtech.jts.geom.Point;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PointLocationDTOMapper {
    PointLocationDTOMapper INSTANCE = Mappers.getMapper(PointLocationDTOMapper.class);

    @Mapping(source = "x", target = "longitude")
    @Mapping(source = "y", target = "latitude")
    LocationDTO pointToLocationDTO(Point point);

    @Mapping(source = "longitude", target = "x")
    @Mapping(source = "latitude", target = "y")
    Point locationDTOToPoint(LocationDTO locationDTO);
}
