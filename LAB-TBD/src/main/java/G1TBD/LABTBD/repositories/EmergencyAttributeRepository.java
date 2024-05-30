package G1TBD.LABTBD.repositories;

import G1TBD.LABTBD.entities.EmergencyAttributeEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmergencyAttributeRepository extends CrudRepository<EmergencyAttributeEntity, Long> {

        //--------------------------CREATE--------------------------
        /*
        @Query(value = "INSERT INTO emergency_attribute (emergency_id, attribute_id, compatibility) " +
                "VALUES (:emergency_id, :attribute_id, :compatibility)", nativeQuery = true)
        @Modifying
        @Transactional
        void create(@Param("emergency_id") long emergency_id,
                    @Param("attribute_id") long attribute_id, @Param("compatibility") boolean compatibility);

         */
        @Modifying
        @Transactional
        @Query(value = "INSERT INTO emergency_attribute (emergency, attribute, compatibility) VALUES (:emergency, :attribute, :compatibility)", nativeQuery = true)
        void create(@Param("emergency") Long emergency, @Param("attribute") Long attribute, @Param("compatibility") boolean compatibility);


        //--------------------------UPDATE--------------------------
        @Query(value = "UPDATE emergency_attribute SET emergency = :emergency, attribute = :attribute, " +
                "compatibility = :compatibility WHERE emergency_attribute_id = :emergency_attribute_id", nativeQuery = true)
        @Modifying
        @Transactional
        void update(@Param("emergency_attribute_id") long emergency_attribute_id, @Param("emergency") long emergency,
                    @Param("attribute") long attribute, @Param("compatibility") boolean compatibility);




        //---------------------------READ---------------------------
        @Query(value = "SELECT * FROM emergency_attribute", nativeQuery = true)
        List<EmergencyAttributeEntity> getAll();


        @Query(value = "SELECT * FROM emergency_attribute WHERE emergency_attribute_id = :emergency_attribute_id", nativeQuery = true)
        EmergencyAttributeEntity getById(@Param("emergency_attribute_id") long emergency_attribute_id);




        //--------------------------DELETE--------------------------

        @Query(value = "DELETE FROM emergency_attribute WHERE emergency_attribute_id = :emergency_attribute_id", nativeQuery = true)
        @Modifying
        @Transactional
        void delete(@Param("emergency_attribute_id") long emergency_attribute_id);



}
