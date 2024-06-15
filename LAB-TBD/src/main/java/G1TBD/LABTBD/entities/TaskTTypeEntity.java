package G1TBD.LABTBD.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "task_ttype")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskTTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long task_ttype_id;

    private Long task_id;
    private Long ttype_id;

}
