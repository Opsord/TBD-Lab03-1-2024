package G1TBD.LABTBD.mongo.user.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Builder
@Document(collection = "skills")
@NoArgsConstructor
@AllArgsConstructor
public class UserSkill {

    @Id
    private String skill_id;

    @Indexed(unique = false)
    private String name;

    @Indexed(unique = false)
    @Field("skill_code")
    private String skillCode;

    private String description;

}
