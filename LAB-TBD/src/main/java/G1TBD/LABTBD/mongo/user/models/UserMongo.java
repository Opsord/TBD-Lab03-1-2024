package G1TBD.LABTBD.mongo.user.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class UserMongo {
    //@BsonId
    //ObjectId id;
    //@Id
    //private int id;
    @Id private String id;
    private String rut;

    private String email;
    private String name;
    private String last_name;
    //Pondré la fecha como string para probar cosas, después cambiarlo
    //private Date birth_date;
    private String birth_date;
    private String sex;
    private String password;
    private String role;
    private boolean availability;
}
