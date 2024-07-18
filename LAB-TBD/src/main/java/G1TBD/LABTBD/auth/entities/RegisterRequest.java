package G1TBD.LABTBD.auth.entities;

import G1TBD.LABTBD.mongo.user.models.UserSkill;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    private String rut;
    private String email;
    private String name;
    private String last_name;
    private LocalDate birth_date;
    private String sex;
    private String password;
    private String role;
    private boolean availability;

    private LocationRequest location;

    private List<UserSkill> skills;

}
