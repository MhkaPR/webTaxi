package ir.mhkapr.webtaxi.repository;

import ir.mhkapr.webtaxi.entity.Order;
import ir.mhkapr.webtaxi.entity.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    @Query(value = "select * from orders where user_id = :userId and status = 0", nativeQuery = true)
    public Optional<Order> findPendingOrderByUserId(@Param("userId") Long userId);

    @Query(value = "select * from orders where user_id = :userId and status = 1", nativeQuery = true)
    public Optional<Order> findPaidOrderByUserId(@Param("userId") Long userId);

    @Query(value = "select * from orders where driver_id = :driverId and status = 1", nativeQuery = true)
    public Optional<Order> findPaidOrderByDriverId(@Param("driverId") Long driverId);
    @Query(value = "select * from orders where driver_id = :driverId and status = 0", nativeQuery = true)
    public Optional<Order> findPendingOrderByDriverId(@Param("driverId") Long driverId);
}
