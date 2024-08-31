package ir.mhkapr.webtaxi.repository;

import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository {
    @Query(value = "select st_distance(" +
            " ST_Transform(ST_SetSRID(:origin, 4326), 3857)," +
            " ST_Transform(ST_SetSRID(:destination, 4326), 3857)) as distance", nativeQuery = true)
    public Double calculateDistance(Point origin,Point destination);
}
