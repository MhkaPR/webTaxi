package ir.mhkapr.webtaxi.entity;

import ir.mhkapr.webtaxi.entity.enums.VehicleType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vehicles")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vehicle_id;
    String licencePlate;
    @Enumerated(EnumType.ORDINAL)
    VehicleType vehicleType;


}
