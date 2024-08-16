package G1TBD.LABTBD.data.point;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PointRepository extends CrudRepository<PointEntity, Long> {

        // --------------------------CREATE--------------------------
/*
        @Query(value = "INSERT INTO point (latitude, longitude) " +
                        "VALUES (:latitude, :longitude)", nativeQuery = true)
        @Modifying
        @Transactional
        PointEntity create(@Param("latitude") double latitude, @Param("longitude") double longitude);

 */


        @Query(value = "INSERT INTO point (latitude, longitude) VALUES (:latitude, :longitude) RETURNING point_id", nativeQuery = true)
        Long create(@Param("latitude") double latitude, @Param("longitude") double longitude);

        // ---------------------------READ---------------------------

        @Query(value = "SELECT * FROM point", nativeQuery = true)
        List<PointEntity> getAll();

        @Query(value = "SELECT * FROM point WHERE point_id = :point_id", nativeQuery = true)
        PointEntity getById(@Param("point_id") Long point_id);

        @Query(value = "SELECT point_id FROM point WHERE latitude = :latitude AND longitude = :longitude ORDER BY point_id DESC LIMIT 1", nativeQuery = true)
        Long findIdByLatitudeAndLongitude(@Param("latitude") double latitude, @Param("longitude") double longitude);

        @Query(value = "SELECT * FROM point " +
                "WHERE ST_DWithin (geom, 'POINT(:latitude :longitude)', :radius) " +
                "LIMIT :limit", nativeQuery = true)
        List<PointEntity> findXNearbyPoints(@Param("latitude") double latitude, @Param("longitude") double longitude,
                                            @Param("radius") double radius, @Param("limit") int limit);


        // Search n users near a point
        @Query(value = "WITH person_w_location AS " +
                "(SELECT * FROM person_point) " +
                "SELECT u.rut FROM person_w_location u " +
                "JOIN point p ON u.point_id = p.point_id " +
                "WHERE ST_DWithin(p.geom, ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326), :radius) " +
                "LIMIT :limit", nativeQuery = true)
        List<String> getXNearbyUsersFromPoint(@Param("latitude") double latitude,
                                         @Param("longitude") double longitude,
                                         @Param("radius") double radius,
                                         @Param("limit") int limit);


        // --------------------------UPDATE--------------------------

        @Query(value = "UPDATE point SET latitude = :latitude, longitude = :longitude " +
                        "WHERE point_id = :point_id", nativeQuery = true)
        @Modifying
        @Transactional
        void update(@Param("point_id") Long point_id, @Param("latitude") double latitude,
                        @Param("longitude") double longitude);

        // --------------------------DELETE--------------------------

        @Query(value = "DELETE FROM point WHERE point_id = :point_id", nativeQuery = true)
        @Modifying
        @Transactional
        void delete(@Param("point_id") Long point_id);

}
