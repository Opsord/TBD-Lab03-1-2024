package G1TBD.LABTBD.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "emergency_point")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmergencyPointEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long emergency_point_id;

    private Long emergency_id;
    private Long point_id;

}
