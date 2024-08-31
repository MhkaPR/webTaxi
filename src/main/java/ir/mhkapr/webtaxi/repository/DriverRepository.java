package ir.mhkapr.webtaxi.repository;

import ir.mhkapr.webtaxi.DTOs.DriverDistanceDTO;
import ir.mhkapr.webtaxi.entity.Driver;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver,Long> {

//    @Query(value = "",nativeQuery = true)
//    public Optional<Driver> findDriverByOrigin(Point origin);

    @Query(value = "select exists(select 1 from drivers where user_id = :userId) as user_exists",nativeQuery = true)
    public Boolean existsDriverByUserId(@Param("userId") Long userId);

    @Query(value = "select *,st_distance(" +
            "st_transform(drivers.location,3857) , " +
            "st_transform(st_setsrid(:point , 4326),3857)) as distance " +
            "from drivers order by distance limit 1", nativeQuery = true)
    public Optional<DriverDistanceDTO> findDriversFromPoint(@Param("point") Point point);




}
