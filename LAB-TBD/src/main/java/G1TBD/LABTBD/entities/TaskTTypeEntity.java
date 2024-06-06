package G1TBD.LABTBD.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "task_t_type")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskTTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long task_t_type_id;

    private Long task_id;
    private Long t_type_id;

}
