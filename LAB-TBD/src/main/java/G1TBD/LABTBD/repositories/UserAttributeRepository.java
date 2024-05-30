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

        //--------------------------CREATE--------------------------
        @Query(value = "INSERT INTO user_attribute (rut, attribute) " +
                "VALUES (:rut, :attribute)", nativeQuery = true)
        @Modifying
        @Transactional
        void create(@Param("rut") String rut, @Param("attribute") long idAttribute);


        //--------------------------UPDATE--------------------------
        @Query(value = "UPDATE user_attribute SET rut = :rut, attribute = :attribute " +
                "WHERE user_attribute_id = :user_attribute_id", nativeQuery = true)
        @Modifying
        @Transactional
        void update(@Param("user_attribute_id") long user_attribute_id, @Param("rut") String rut, @Param("attribute") long idAttribute);


        //---------------------------READ---------------------------
        @Query(value = "SELECT * FROM user_attribute", nativeQuery = true)
        List<UserAttributeEntity> getAll();

        @Query(value = "SELECT * FROM user_attribute WHERE user_attribute_id = :user_attribute_id", nativeQuery = true)
        UserAttributeEntity getById(@Param("user_attribute_id") long user_attribute_id);

        @Query(value = "SELECT * FROM user_attribute WHERE rut = :rut", nativeQuery = true)
        List<UserAttributeEntity> getByRut(@Param("rut") String rut);

        @Query(value = "SELECT * FROM user_attribute WHERE attribute = :attribute", nativeQuery = true)
        List<UserAttributeEntity> getByAttributeId(@Param("attribute") long attribute);


        //--------------------------DELETE--------------------------
        @Query(value = "DELETE FROM user_attribute WHERE user_attribute_id = :user_attribute_id", nativeQuery = true)
        @Modifying
        @Transactional
        void delete(@Param("user_attribute_id") long user_attribute_id);



}
