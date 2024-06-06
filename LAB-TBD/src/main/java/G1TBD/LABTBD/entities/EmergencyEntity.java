package G1TBD.LABTBD.entities;

import G1TBD.LABTBD.data.point.PointEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "emergency")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmergencyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long emergency_id;

    private boolean status;
    private String title;
    private String description;

}
