package G1TBD.LABTBD.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "tasks")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long task_id;

    private String type;
    private String description;
    private boolean state;

    @ManyToOne
    @JoinColumn(name = "emergency")
    private EmergencyEntity emergency;

}
