package G1TBD.LABTBD.services;

import G1TBD.LABTBD.entities.TaskEntity;
import G1TBD.LABTBD.entities.TaskUserEntity;
import G1TBD.LABTBD.entities.UserEntity;
import G1TBD.LABTBD.repositories.TaskRepository;
import G1TBD.LABTBD.repositories.TaskUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskUserRepository taskUserRepository;
    private static final Logger logger = Logger.getLogger(TaskService.class.getName());

    @Autowired
    public TaskService(TaskRepository taskRepository, TaskUserRepository taskUserRepository) {
        this.taskRepository = taskRepository;
        this.taskUserRepository = taskUserRepository;
    }

    //--------------------------CREATE--------------------------
    public void create(TaskEntity task) {
        taskRepository.create(
                task.getEmergency().getEmergency_id(),
                task.getType(),
                task.getDescription(),
                task.isState());
        logger.info("Task created");
    }


    //--------------------------UPDATE--------------------------
    public void update(TaskEntity task) {
        taskRepository.update(
                task.getTask_id(),
                task.getEmergency().getEmergency_id(),
                task.getType(),
                task.getDescription(),
                task.isState());
        logger.info("Task updated");
    }


    //---------------------------READ---------------------------
    public List<TaskEntity> getAll() {
        return taskRepository.getAll();
    }

    public TaskEntity getById(long id) {
        return taskRepository.getById(id);
    }

    public List<TaskEntity> getByEmergencyId(long id) {
        return taskRepository.getByEmergencyId(id);
    }

    public List<UserEntity> getAllVolunteers(long task_id) {
        List<TaskUserEntity> taskUserEntities = taskUserRepository.getVolunteersByTaskId(task_id);
        List<UserEntity> volunteers = new ArrayList<>();
        for (TaskUserEntity taskUserEntity : taskUserEntities) {
            volunteers.add(taskUserEntity.getUser());
        }
        return volunteers;
    }

    public List<TaskEntity> getTasksByVolunteer(String volunteer) {
        List<TaskUserEntity> taskUserEntities = taskUserRepository.getByVolunteer(volunteer);
        List<TaskEntity> tasks = new ArrayList<>();
        for (TaskUserEntity taskUserEntity : taskUserEntities) {
            tasks.add(taskUserEntity.getTask());
        }
        return tasks;
    }


    //--------------------------DELETE--------------------------
    public void delete(long id) {
        taskRepository.delete(id);
        logger.info("Task deleted");
    }


}
