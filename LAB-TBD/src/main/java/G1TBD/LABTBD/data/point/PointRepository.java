package G1TBD.LABTBD.data.point;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PointRepository extends CrudRepository<PointEntity, Long>{

    @Query(value = "INSERT INTO points (latitude, longitude) " +
            "VALUES (:latitude, :longitude)", nativeQuery = true)
    @Modifying
    @Transactional
    void create(@Param("latitude") double latitude, @Param("longitude") double longitude);

    @Query(value = "SELECT * FROM points", nativeQuery = true)
    List<PointEntity> getAll();

    @Query(value = "SELECT * FROM points WHERE point_id = :point_id", nativeQuery = true)
    PointEntity getById(@Param("point_id") long point_id);

    @Query(value = "SELECT point_id FROM points WHERE latitude = :latitude AND longitude = :longitude ORDER BY point_id DESC LIMIT 1", nativeQuery = true)
    Long findIdByLatitudeAndLongitude(@Param("latitude") double latitude, @Param("longitude") double longitude);


    @Query(value = "UPDATE points SET latitude = :latitude, longitude = :longitude " +
            "WHERE point_id = :point_id", nativeQuery = true)
    @Modifying
    @Transactional
    void update(@Param("point_id") long point_id, @Param("latitude") double latitude,
                @Param("longitude") double longitude);

    @Query(value = "DELETE FROM points WHERE point_id = :point_id", nativeQuery = true)
    @Modifying
    @Transactional
    void delete(@Param("point_id") long point_id);

    @Query(value = "SELECT * FROM points " +
            "WHERE ST_DWithin (geom, 'POINT(:latitude :longitude)', :radius) " +
            "LIMIT :limit", nativeQuery = true)
    List<PointEntity> findXNearbyPoints(@Param("latitude") double latitude, @Param("longitude") double longitude,
                                        @Param("radius") double radius, @Param("limit") int limit);

}