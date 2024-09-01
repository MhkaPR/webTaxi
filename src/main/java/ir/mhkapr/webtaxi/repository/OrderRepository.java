package ir.mhkapr.webtaxi.repository;

import ir.mhkapr.webtaxi.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    public Optional<Order> findOrderByUserId(Long userId);
}
