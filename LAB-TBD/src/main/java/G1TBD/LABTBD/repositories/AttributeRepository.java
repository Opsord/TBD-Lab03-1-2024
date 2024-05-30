package G1TBD.LABTBD.repositories;

import G1TBD.LABTBD.entities.AttributeEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttributeRepository extends CrudRepository<AttributeEntity, Long> {

    //--------------------------CREATE--------------------------
    @Query(value = "INSERT INTO attributes (attribute) VALUES (:attribute)", nativeQuery = true)
    @Modifying
    @Transactional
    void create(@Param("attribute") String attribute);


    //--------------------------UPDATE--------------------------
    @Query(value = "UPDATE attributes SET attribute = :attribute WHERE attribute_id = :attribute_id", nativeQuery = true)
    @Modifying
    @Transactional
    void update(@Param("attribute_id") long attribute_id, @Param("attribute") String attribute);


    //---------------------------READ---------------------------
    @Query(value = "SELECT * FROM attributes", nativeQuery = true)
    List<AttributeEntity> getAll();

    @Query(value = "SELECT * FROM attributes WHERE attribute_id = :attribute_id", nativeQuery = true)
    AttributeEntity getById(@Param("attribute_id") long attribute_id);

    //--------------------------DELETE--------------------------
    @Query(value = "DELETE FROM attributes WHERE attribute_id = :attribute_id", nativeQuery = true)
    @Modifying
    @Transactional
    void delete(@Param("attribute_id") long attribute_id);

}