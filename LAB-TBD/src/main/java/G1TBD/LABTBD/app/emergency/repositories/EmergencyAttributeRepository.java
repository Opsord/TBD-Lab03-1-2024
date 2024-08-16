package G1TBD.LABTBD.app.emergency.repositories;

import G1TBD.LABTBD.app.emergency.entities.EmergencyAttributeEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmergencyAttributeRepository extends CrudRepository<EmergencyAttributeEntity, Long> {

        // --------------------------CREATE--------------------------

        @Query(value = "INSERT INTO emergency_attribute (" +
                        "emergency_id, skill_code, compatibility) " +
                        "VALUES (:emergency_id, :skill_code, :compatibility)", nativeQuery = true)
        @Modifying
        @Transactional
        void create(@Param("emergency_id") Long emergency_id,
                        @Param("skill_code") String skill_code,
                        @Param("compatibility") boolean compatibility);

        // ---------------------------READ---------------------------

        @Query(value = "SELECT * FROM emergency_attribute", nativeQuery = true)
        List<EmergencyAttributeEntity> getAll();

        @Query(value = "SELECT * FROM emergency_attribute WHERE emergency_attribute_id = :emergency_attribute_id", nativeQuery = true)
        EmergencyAttributeEntity getById(@Param("emergency_attribute_id") Long emergency_attribute_id);

        @Query(value = "SELECT * FROM emergency_attribute WHERE emergency_id = :emergency_id", nativeQuery = true)
        List<EmergencyAttributeEntity> getByEmergencyId(@Param("emergency_id") Long emergency_id);

        @Query(value = "SELECT * FROM emergency_attribute WHERE skill_code = :skill_code", nativeQuery = true)
        List<EmergencyAttributeEntity> getBySkillCode(@Param("skill_code") String skill_code);

        // --------------------------UPDATE--------------------------

        @Query(value = "UPDATE emergency_attribute SET emergency_id = :emergency_id, skill_code = :skill_code, " +
                        "compatibility = :compatibility WHERE emergency_attribute_id = :emergency_attribute_id", nativeQuery = true)
        @Modifying
        @Transactional
        void update(@Param("emergency_attribute_id") Long emergency_attribute_id,
                        @Param("emergency_id") Long emergency_id,
                        @Param("skill_code") String skill_code,
                        @Param("compatibility") boolean compatibility);

        // --------------------------DELETE--------------------------

        @Query(value = "DELETE FROM emergency_attribute WHERE emergency_attribute_id = :emergency_attribute_id", nativeQuery = true)
        @Modifying
        @Transactional
        void delete(@Param("emergency_attribute_id") Long emergency_attribute_id);

}
