package G1TBD.LABTBD.app.emergency.repositories;

import G1TBD.LABTBD.app.emergency.entities.EmergencyEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmergencyRepository extends CrudRepository<EmergencyEntity, Long> {

        // --------------------------CREATE--------------------------

        @Query(value = "INSERT INTO emergency (title, description, status) " +
                        "VALUES (:title, :description, :status)", nativeQuery = true)
        @Modifying
        @Transactional
        void create(@Param("title") String title,
                        @Param("description") String description,
                        @Param("status") boolean status);

        // ---------------------------READ---------------------------

        @Query(value = "SELECT * FROM emergency", nativeQuery = true)
        List<EmergencyEntity> getAll();

        @Query(value = "SELECT * FROM emergency WHERE emergency_id = :emergency_id ", nativeQuery = true)
        EmergencyEntity getById(@Param("emergency_id") Long id);

        @Query(value = "SELECT * FROM emergency WHERE status = true", nativeQuery = true)
        List<EmergencyEntity> getAllActive();

        @Query(value = "SELECT * FROM emergency WHERE status = false", nativeQuery = true)
        List<EmergencyEntity> getAllClosed();

        @Query(value = "SELECT * FROM emergency " +
                        "WHERE title = :title AND description = :description " +
                        "ORDER BY emergency_id DESC LIMIT 1", nativeQuery = true)
        EmergencyEntity findLatestEmergencyId(@Param("title") String title,
                        @Param("description") String description);

        // --------------------------UPDATE--------------------------

        @Query(value = "UPDATE emergency SET title = :title, description = :description, " +
                        "status = :status WHERE emergency_id = :emergency_id ", nativeQuery = true)
        @Modifying
        @Transactional
        void update(@Param("emergency_id ") Long emergency_id,
                        @Param("title") String title,
                        @Param("description") String description,
                        @Param("status") boolean status);

        // --------------------------DELETE--------------------------

        @Query(value = "DELETE FROM emergency WHERE emergency_id = :emergency_id ", nativeQuery = true)
        @Modifying
        @Transactional
        void delete(@Param("emergency_id") Long emergency_id);

}