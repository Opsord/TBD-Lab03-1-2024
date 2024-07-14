package G1TBD.LABTBD.app.emergency.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "emergency_attribute")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmergencyAttributeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long emergency_attribute_id;

    private Long emergency_id;
    private Long attribute_id;

    private boolean compatibility;

}
