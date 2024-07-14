package G1TBD.LABTBD.app.user.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "person_institution")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInstitutionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_institution_id")
    private Long user_institution_id;

    private String rut;
    private Long institution_id;

}