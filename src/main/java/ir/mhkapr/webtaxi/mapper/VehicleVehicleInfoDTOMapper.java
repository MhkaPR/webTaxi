package ir.mhkapr.webtaxi.mapper;


import ir.mhkapr.webtaxi.DTOs.VehicleInfoDTO;
import ir.mhkapr.webtaxi.entity.Vehicle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface VehicleVehicleInfoDTOMapper {
    VehicleVehicleInfoDTOMapper INSTANCE = Mappers.getMapper(VehicleVehicleInfoDTOMapper.class);

    @Mapping(target = "vehicleType", source = "vehicleType")
    VehicleInfoDTO VehicleToVehicleInfoDTO(Vehicle vehicle);

    @Mapping(target = "vehicleType", source = "vehicleType")
    Vehicle VehicleInfoDTOToVehicle(VehicleInfoDTO vehicleInfo);
}
