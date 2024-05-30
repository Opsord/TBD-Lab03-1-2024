package G1TBD.LABTBD.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "attributes")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttributeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long attribute_id;

    private String attribute;

}
