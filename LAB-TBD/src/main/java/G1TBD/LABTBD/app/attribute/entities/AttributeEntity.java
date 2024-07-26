package G1TBD.LABTBD.app.attribute.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "attribute")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttributeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long attribute_id;

    private String skill_id;
    private String name;
    private String description;
}