package G1TBD.LABTBD.repositories;

import G1TBD.LABTBD.entities.TaskEmergencyEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskEmergencyRepository extends CrudRepository<TaskEmergencyEntity, Long> {

    // --------------------------CREATE--------------------------

    @Query(value = "INSERT INTO task_emergency (task_id, emergency_id) VALUES (:task_id, :emergency_id)", nativeQuery = true)
    @Modifying
    @Transactional
    void create(@Param("task_id") Long task_id,
            @Param("emergency_id") Long emergency_id);

    // ---------------------------READ---------------------------

    @Query(value = "SELECT * FROM task_emergency", nativeQuery = true)
    List<TaskEmergencyEntity> getAll();

    @Query(value = "SELECT * FROM task_emergency WHERE task_emergency_id = :task_emergency_id", nativeQuery = true)
    TaskEmergencyEntity getById(@Param("task_emergency_id") Long task_emergency_id);

    @Query(value = "SELECT * FROM task_emergency WHERE task_id = :task_id", nativeQuery = true)
    List<TaskEmergencyEntity> getEmergenciesByTaskId(@Param("task_id") Long task_id);

    @Query(value = "SELECT * FROM task_emergency WHERE emergency_id = :emergency_id", nativeQuery = true)
    List<TaskEmergencyEntity> getTasksByEmergencyId(@Param("emergency_id") Long emergency_id);

    // --------------------------UPDATE--------------------------

    @Query(value = "UPDATE task_emergency SET task_id = :task_id, emergency_id = :emergency_id " +
            "WHERE task_emergency_id = :task_emergency_id", nativeQuery = true)
    @Modifying
    @Transactional
    void update(@Param("task_emergency_id") Long task_emergency_id,
            @Param("task_id") Long task_id,
            @Param("emergency_id") Long emergency_id);

    // --------------------------DELETE--------------------------

    @Query(value = "DELETE FROM task_emergency WHERE task_emergency_id = :task_emergency_id", nativeQuery = true)
    @Modifying
    @Transactional
    void delete(@Param("task_emergency_id") Long task_emergency_id);

}
