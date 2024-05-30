package G1TBD.LABTBD.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@Table(name = "institutions")
@NoArgsConstructor
@AllArgsConstructor
public class InstitutionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long institution_id;

    private String name;

}
