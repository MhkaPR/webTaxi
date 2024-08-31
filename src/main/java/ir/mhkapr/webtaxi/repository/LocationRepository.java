package ir.mhkapr.webtaxi.repository;

import ir.mhkapr.webtaxi.entity.Driver;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Driver,Long> {
    @Query(value = "select st_distance(" +
            " ST_Transform(ST_SetSRID(:origin, 4326), 3857)," +
            " ST_Transform(ST_SetSRID(:destination, 4326), 3857)) as distance", nativeQuery = true)
    public Double calculateDistance(Point origin,Point destination);
}
