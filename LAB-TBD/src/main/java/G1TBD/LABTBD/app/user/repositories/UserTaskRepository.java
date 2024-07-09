package G1TBD.LABTBD.app.user.repositories;

import G1TBD.LABTBD.app.user.entities.UserTaskEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTaskRepository extends CrudRepository<UserTaskEntity, Long> {

    //--------------------------CREATE--------------------------

    @Query(value = "INSERT INTO person_task (task_id, rut) VALUES (:task_id, :rut)", nativeQuery = true)
    @Modifying
    @Transactional
    void create(@Param("task_id") Long task_id, @Param("rut") String rut);

    //---------------------------READ---------------------------

    @Query(value = "SELECT * FROM person_task", nativeQuery = true)
    List<UserTaskEntity> getAll();

    @Query(value = "SELECT * FROM person_task WHERE person_task_id = :person_task_id", nativeQuery = true)
    UserTaskEntity getById(@Param("person_task_id") Long person_task_id);

    // Get all volunteers from a task_id
    @Query(value = "SELECT * FROM person_task WHERE task_id = :task_id", nativeQuery = true)
    List<UserTaskEntity> getVolunteersByTaskId(@Param("task_id") Long task_id);

    // Get all tasks from a volunteer
    @Query(value = "SELECT * FROM person_task WHERE rut = :rut", nativeQuery = true)
    List<UserTaskEntity> getTaskByVolunteer(@Param("rut") String rut);

    //--------------------------UPDATE--------------------------

    @Query(value = "UPDATE person_task SET task_id = :task_id, rut = :rut WHERE person_task_id = :person_task_id", nativeQuery = true)
    @Modifying
    @Transactional
    void update(@Param("person_task_id") Long person_task_id, @Param("task_id") Long task_id, @Param("rut") String rut);

    //--------------------------DELETE--------------------------

    @Query(value = "DELETE FROM person_task WHERE person_task_id = :person_task_id", nativeQuery = true)
    @Modifying
    @Transactional
    void delete(@Param("person_task_id") Long person_task_id);

}
