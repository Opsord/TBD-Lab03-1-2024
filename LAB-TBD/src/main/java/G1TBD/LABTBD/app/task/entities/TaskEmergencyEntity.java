package G1TBD.LABTBD.app.task.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "task_emergency")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskEmergencyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long task_emergency_id;

    private Long task_id;
    private Long emergency_id;

}
