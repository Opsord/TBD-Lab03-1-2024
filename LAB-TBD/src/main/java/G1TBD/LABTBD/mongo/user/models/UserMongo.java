package G1TBD.LABTBD.mongo.user.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class UserMongo implements UserDetails {

    @Id
    private String user_id;

    @Indexed(unique = true)
    private String rut;

    @Indexed(unique = true)
    private String email;

    private String name;
    private String last_name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birth_date;

    private String sex;
    private String password;
    private boolean availability;

    private UserRole role;
    private transient List<UserSkill> skills;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return rut;
    }
}
