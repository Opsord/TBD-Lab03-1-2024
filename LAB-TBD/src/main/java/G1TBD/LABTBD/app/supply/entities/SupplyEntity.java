package G1TBD.LABTBD.app.supply.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "supply")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupplyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long supply_id;
    private String name;
    private String description;
    private String classification;
}
