package G1TBD.LABTBD.repositories;

import G1TBD.LABTBD.entities.UserAttributeEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAttributeRepository extends CrudRepository<UserAttributeEntity, Long> {

        // --------------------------CREATE--------------------------

        @Query(value = "INSERT INTO person_attribute (rut, attribute_id) " +
                        "VALUES (:rut, :attribute_id)", nativeQuery = true)
        @Modifying
        @Transactional
        void create(@Param("rut") String rut,
                        @Param("attribute_id") Long attribute_id);

        // ---------------------------READ---------------------------

        @Query(value = "SELECT * FROM person_attribute", nativeQuery = true)
        List<UserAttributeEntity> getAll();

        @Query(value = "SELECT * FROM person_attribute WHERE person_attribute_id = :person_attribute_id", nativeQuery = true)
        UserAttributeEntity getById(@Param("person_attribute_id") Long person_attribute_id);

        @Query(value = "SELECT * FROM person_attribute WHERE rut = :rut", nativeQuery = true)
        List<UserAttributeEntity> getByRut(@Param("rut") String rut);

        @Query(value = "SELECT * FROM person_attribute WHERE attribute_id = :attribute_id", nativeQuery = true)
        List<UserAttributeEntity> getByAttributeId(@Param("attribute_id") Long attribute_id);

        // --------------------------UPDATE--------------------------

        @Query(value = "UPDATE person_attribute SET rut = :rut, attribute_id = :attribute_id " +
                        "WHERE person_attribute_id = :person_attribute_id", nativeQuery = true)
        @Modifying
        @Transactional
        void update(@Param("person_attribute_id") Long person_attribute_id,
                        @Param("rut") String rut,
                        @Param("attribute") Long idAttribute);

        // --------------------------DELETE--------------------------

        @Query(value = "DELETE FROM person_attribute WHERE person_attribute_id = :person_attribute_id", nativeQuery = true)
        @Modifying
        @Transactional
        void delete(@Param("person_attribute_id") Long person_attribute_id);

}
