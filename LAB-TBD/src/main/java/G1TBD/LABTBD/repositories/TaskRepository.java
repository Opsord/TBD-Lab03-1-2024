package G1TBD.LABTBD.repositories;

import G1TBD.LABTBD.entities.TaskEntity;
import G1TBD.LABTBD.entities.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends CrudRepository<TaskEntity, Long> {

        //--------------------------CREATE--------------------------
        @Query(value = "INSERT INTO tasks (emergency, type, description, state)  " +
                "VALUES (:emergency, :type, :description, :state)", nativeQuery = true)
        @Modifying
        @Transactional
        void create(@Param("emergency") long emergency_id, @Param("type") String type,
                    @Param("description") String description, @Param("state") boolean state);


        //--------------------------UPDATE--------------------------
        @Query(value = "UPDATE tasks SET emergency = :emergency, type = :type, description = :description, " +
                "state = :state WHERE task_id = :task_id", nativeQuery = true)
        @Modifying
        @Transactional
        void update(@Param("task_id") long id, @Param("emergency") long emergency_id, @Param("type") String type,
                    @Param("description") String description, @Param("state") boolean state);


        //---------------------------READ---------------------------
        @Query(value = "SELECT * FROM tasks", nativeQuery = true)
        List<TaskEntity> getAll();

        @Query(value = "SELECT * FROM tasks WHERE task_id = :task_id", nativeQuery = true)
        TaskEntity getById(@Param("task_id") long task_id);

        @Query(value= "SELECT * FROM tasks WHERE emergency = :emergency", nativeQuery = true)
        List<TaskEntity> getByEmergencyId(@Param("emergency") long emergency );


        //--------------------------DELETE--------------------------
        @Query(value = "DELETE FROM tasks WHERE task_id = :task_id", nativeQuery = true)
        @Modifying
        @Transactional
        void delete(@Param("task_id") long task_id);


}
