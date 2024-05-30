package G1TBD.LABTBD.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "task_user")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long task_user_id;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private TaskEntity task;

    @ManyToOne
    @JoinColumn(name = "rut")
    private UserEntity user;

}
