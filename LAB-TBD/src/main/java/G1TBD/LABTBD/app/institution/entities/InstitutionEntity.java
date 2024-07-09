package G1TBD.LABTBD.app.institution.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "institution")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InstitutionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long institution_id;

    private String name;

}
