package G1TBD.LABTBD.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "user_institution")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInstitutionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long user_institution_id;

    @ManyToOne
    @JoinColumn(name = "rut")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "institution_id")
    private InstitutionEntity institution;

}