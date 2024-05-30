package G1TBD.LABTBD.entities;

import G1TBD.LABTBD.data.point.PointEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "emergencies")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmergencyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long emergency_id;

    private boolean status;
    private String title;
    private String description;

    @OneToOne
    @JoinColumn(name = "coordinator")
    private UserEntity coordinator;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location", referencedColumnName = "point_id")
    private PointEntity location;

}
