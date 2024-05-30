package G1TBD.LABTBD.repositories;

import G1TBD.LABTBD.entities.TaskUserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskUserRepository extends CrudRepository<TaskUserEntity, Long> {

    //--------------------------CREATE--------------------------
    @Query(value = "INSERT INTO task_user (task, volunteer) VALUES (:task, :volunteer)", nativeQuery = true)
    @Modifying
    @Transactional
    void create(@Param("task") long task, @Param("volunteer") String volunteer);


    //--------------------------UPDATE--------------------------
    @Query(value = "UPDATE task_user SET task = :task, volunteer = :volunteer WHERE task_user_id = :task_user_id", nativeQuery = true)
    @Modifying
    @Transactional
    void update(@Param("task_user_id") long task_user_id, @Param("task") long task, @Param("volunteer") String volunteer);


    //---------------------------READ---------------------------
    @Query(value = "SELECT * FROM task_user", nativeQuery = true)
    List<TaskUserEntity> getAll();

    @Query(value = "SELECT * FROM task_user WHERE task_user_id = :task_user_id", nativeQuery = true)
    TaskUserEntity getById(@Param("task_user_id") long task_user_id);

    // Get all volunteers from a task
    @Query(value = "SELECT * FROM task_user WHERE task = :task", nativeQuery = true)
    List<TaskUserEntity> getVolunteersByTaskId(@Param("task") long task);

    // Get all tasks from a volunteer
    @Query(value = "SELECT * FROM task_user WHERE volunteer = :volunteer", nativeQuery = true)
    List<TaskUserEntity> getByVolunteer(@Param("volunteer") String volunteer);


    //--------------------------DELETE--------------------------
    @Query(value = "DELETE FROM task_user WHERE task_user_id = :task_user_id", nativeQuery = true)
    @Modifying
    @Transactional
    void delete(@Param("task_user_id") long task_user_id);


}
