package G1TBD.LABTBD.app.user.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "person_task")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserTaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_task_id")
    private Long user_task_id;

    private Long task_id;
    private String rut;

}
