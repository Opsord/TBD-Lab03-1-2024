package G1TBD.LABTBD.services;

import G1TBD.LABTBD.entities.UserTaskEntity;
import G1TBD.LABTBD.repositories.UserTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserTaskService {

    private final UserTaskRepository userTaskRepository;

    @Autowired
    public UserTaskService(UserTaskRepository userTaskRepository) {
        this.userTaskRepository = userTaskRepository;
    }

    // --------------------------CREATE--------------------------

    public void create(UserTaskEntity userTask) {
        userTaskRepository.create(userTask.getTask_id(),userTask.getRut());
    }

    // ---------------------------READ---------------------------

    public List<UserTaskEntity> getAll() {
        return userTaskRepository.getAll();
    }

    public UserTaskEntity getById(Long user_task_id) {
        return userTaskRepository.getById(user_task_id);
    }

    public List<UserTaskEntity> getVolunteersByTaskId(Long task_id) {
        return userTaskRepository.getVolunteersByTaskId(task_id);
    }

    public List<UserTaskEntity> getTasksByVolunteer(String rut) {
        return userTaskRepository.getTaskByVolunteer(rut);
    }

    // --------------------------UPDATE--------------------------

    public void update(UserTaskEntity userTask) {
        userTaskRepository.update(userTask.getUser_task_id(),userTask.getTask_id(),userTask.getRut());
    }

    // --------------------------DELETE--------------------------

    public void delete(Long user_task_id) {
        userTaskRepository.delete(user_task_id);
    }

}
