package G1TBD.LABTBD.app.task.repositories;

import G1TBD.LABTBD.app.task.entities.TaskEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends CrudRepository<TaskEntity, Long> {

        // --------------------------CREATE--------------------------

        @Query(value = "INSERT INTO task (description, status)  " +
                        "VALUES (:description, :status)", nativeQuery = true)
        @Modifying
        @Transactional
        void create(@Param("description") String description, @Param("status") boolean status);

        // --------------------------UPDATE--------------------------

        @Query(value = "UPDATE task SET description = :description, status = :status " +
                        "WHERE task_id = :task_id", nativeQuery = true)
        @Modifying
        @Transactional
        void update(@Param("task_id") Long task_id,
                        @Param("description") String description,
                        @Param("status") boolean status);

        // ---------------------------READ---------------------------

        @Query(value = "SELECT * FROM task", nativeQuery = true)
        List<TaskEntity> getAll();

        @Query(value = "SELECT * FROM task WHERE task_id = :task_id", nativeQuery = true)
        TaskEntity getById(@Param("task_id") Long task_id);

        // --------------------------DELETE--------------------------

        @Query(value = "DELETE FROM task WHERE task_id = :task_id", nativeQuery = true)
        @Modifying
        @Transactional
        void delete(@Param("task_id") Long task_id);

}
