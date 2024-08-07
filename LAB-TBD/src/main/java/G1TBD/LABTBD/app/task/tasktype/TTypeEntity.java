package G1TBD.LABTBD.app.task.tasktype;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "ttype")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ttype_id;

    private String type;
}
