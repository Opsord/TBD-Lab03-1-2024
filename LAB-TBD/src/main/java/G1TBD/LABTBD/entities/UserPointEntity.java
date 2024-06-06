package G1TBD.LABTBD.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "person_point")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPointEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_point_id")
    private Long user_point_id;

    private String rut;
    private Long point_id;
}
