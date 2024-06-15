package G1TBD.LABTBD.services;

import G1TBD.LABTBD.entities.TaskEntity;
import G1TBD.LABTBD.entities.UserTaskEntity;
import G1TBD.LABTBD.entities.UserEntity;
import G1TBD.LABTBD.repositories.TaskRepository;
import G1TBD.LABTBD.repositories.UserTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserTaskRepository userTaskRepository;
    private static final Logger logger = Logger.getLogger(TaskService.class.getName());

    @Autowired
    public TaskService(TaskRepository taskRepository, UserTaskRepository userTaskRepository) {
        this.taskRepository = taskRepository;
        this.userTaskRepository = userTaskRepository;
    }

    // --------------------------CREATE--------------------------
    public void create(TaskEntity task) {
        taskRepository.create(
                task.getEmergency().getEmergency_id(),
                task.getType(),
                task.getDescription(),
                task.isStatus());
        logger.info("Task created");
    }

    // --------------------------UPDATE--------------------------
    public void update(TaskEntity task) {
        taskRepository.update(
                task.getTask_id(),
                task.getEmergency().getEmergency_id(),
                task.getType(),
                task.getDescription(),
                task.isStatus());
        logger.info("Task updated");
    }

    // ---------------------------READ---------------------------
    public List<TaskEntity> getAll() {
        return taskRepository.getAll();
    }

    public TaskEntity getById(Long id) {
        return taskRepository.getById(id);
    }

    public List<TaskEntity> getByEmergencyId(Long id) {
        return taskRepository.getByEmergencyId(id);
    }

    public List<UserEntity> getAllVolunteers(Long task_id) {
        List<UserTaskEntity> taskUserEntities = userTaskRepository.getVolunteersByTaskId(task_id);
        List<UserEntity> volunteers = new ArrayList<>();
        for (UserTaskEntity taskUserEntity : taskUserEntities) {
            volunteers.add(taskUserEntity.getUser());
        }
        return volunteers;
    }

    public List<TaskEntity> getTasksByVolunteer(String volunteer) {
        List<UserTaskEntity> taskUserEntities = userTaskRepository.getByVolunteer(volunteer);
        List<TaskEntity> tasks = new ArrayList<>();
        for (UserTaskEntity taskUserEntity : taskUserEntities) {
            tasks.add(taskUserEntity.getTask());
        }
        return tasks;
    }

    // --------------------------DELETE--------------------------
    public void delete(Long id) {
        taskRepository.delete(id);
        logger.info("Task deleted");
    }

}
