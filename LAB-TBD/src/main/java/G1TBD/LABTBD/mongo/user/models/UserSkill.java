package G1TBD.LABTBD.mongo.user.models;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "skills")
@NoArgsConstructor
@AllArgsConstructor
public class UserSkill {

    @Id
    private String skill_id;

    @Indexed(unique = true)
    private String name;

    private String description;

}
