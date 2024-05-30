package G1TBD.LABTBD.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "user_attribute")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAttributeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long user_attribute_id;

    @ManyToOne
    @JoinColumn(name = "rut")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "attribute_id")
    private AttributeEntity attribute;

}