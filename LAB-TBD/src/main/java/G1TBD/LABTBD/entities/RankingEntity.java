package G1TBD.LABTBD.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "rankings")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RankingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ranking_id;

    private Integer value;

    @ManyToOne
    @JoinColumn(name = "rut")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private TaskEntity task;

}
