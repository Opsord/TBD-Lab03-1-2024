package G1TBD.LABTBD.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "emergency_person")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmergencyUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long emergency_person_id;

    private String rut;
    private Long emergency_id;
}
