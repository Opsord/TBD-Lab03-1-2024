package G1TBD.LABTBD.app.inventory.repositories;

import G1TBD.LABTBD.app.inventory.entities.InventoryEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends CrudRepository<InventoryEntity, Long> {

    // --------------------------CREATE--------------------------

    @Query(value = "INSERT INTO inventory (supply_id, emergency_id, requested, stock, missing, priority) " +
            "VALUES (:supply_id, :emergency_id, :requested, :stock, :missing, :priority)", nativeQuery = true)
    @Modifying
    @Transactional
    void create(@Param("supply_id") Long supply_id,
                @Param("emergency_id") Long emergency_id,
                @Param("requested") Integer requested,
                @Param("stock") Integer stock,
                @Param("missing") String missing,
                @Param("priority") String priority);

    // ---------------------------READ---------------------------

    @Query(value = "SELECT * FROM inventory", nativeQuery = true)
    List<InventoryEntity> getAll();

    @Query(value = "SELECT * FROM inventory WHERE inventory_id = :inventory_id", nativeQuery = true)
    InventoryEntity getById(@Param("inventory_id") Long inventory_id);

    // --------------------------UPDATE--------------------------

    @Query(value = "UPDATE inventory SET supply_id = :supply_id, emergency_id = :emergency_id, " +
            "requested = :requested, stock = :stock, missing = :missing, priority = :priority" +
            " WHERE inventory_id = :inventory_id", nativeQuery = true)
    @Modifying
    @Transactional
    void update(@Param("supply_id") Long supply_id,
                @Param("emergency_id") Long emergency_id,
                @Param("requested") Integer requested,
                @Param("stock") Integer stock,
                @Param("missing") String missing,
                @Param("priority") String priority,
                @Param("inventory_id") Long inventory_id);

    // --------------------------DELETE--------------------------

    @Query(value = "DELETE FROM inventory WHERE inventory_id = :inventory_id", nativeQuery = true)
    @Modifying
    @Transactional
    void delete(@Param("inventory_id") Long inventory_id);

}
