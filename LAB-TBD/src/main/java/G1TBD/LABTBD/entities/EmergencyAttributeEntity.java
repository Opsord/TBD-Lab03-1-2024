package G1TBD.LABTBD.entities;

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
    private long emergency_attribute_id;

    private Long emergency; //Llave foranea de EmergencyEntity
    private Long attribute; //Llave foranea de AttributeEntity

    private boolean compatibility;

}
