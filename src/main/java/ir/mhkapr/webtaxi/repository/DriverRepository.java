package ir.mhkapr.webtaxi.repository;

import ir.mhkapr.webtaxi.entity.Driver;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver,Long> {

    @Query(value = "",nativeQuery = true)
    public Optional<Driver> findDriverByOrigin(Point origin);

}
