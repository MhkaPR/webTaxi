package ir.mhkapr.webtaxi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import ir.mhkapr.webtaxi.entity.enums.OrderStatus;
import ir.mhkapr.webtaxi.entity.enums.OrderType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long order_id;
    Point origin;
    Point destination;
    @Enumerated(EnumType.ORDINAL)
    OrderType type;
    Double price;
    @DateTimeFormat(pattern = "yyyy-MM-dd/hh-mm-ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd/hh-mm-ss")
    Date createdAt;
    @DateTimeFormat(pattern = "yyyy-MM-dd/hh-mm-ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd/hh-mm-ss")
    Date finishedAt;
    Long userId;
    Long driverId;
    @Enumerated(EnumType.ORDINAL)
    OrderStatus status;
}
