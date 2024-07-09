package G1TBD.LABTBD.app.user.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "person_attribute")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAttributeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_attribute_id")
    private Long user_attribute_id;

    private String rut;
    private Long attribute_id;

}