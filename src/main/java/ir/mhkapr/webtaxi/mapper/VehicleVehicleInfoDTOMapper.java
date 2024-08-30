package ir.mhkapr.webtaxi.mapper;


import ir.mhkapr.webtaxi.DTOs.VehicleInfoDTO;
import ir.mhkapr.webtaxi.entity.Vehicle;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface VehicleVehicleInfoDTOMapper {
    VehicleVehicleInfoDTOMapper INSTANCE = Mappers.getMapper(VehicleVehicleInfoDTOMapper.class);

    VehicleInfoDTO VehicleToVehicleInfoDTO(Vehicle vehicle);

    Vehicle VehicleInfoDTOToVehicle(VehicleInfoDTO vehicleInfo);
}
