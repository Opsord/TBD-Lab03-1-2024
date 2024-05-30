package G1TBD.LABTBD.entities;

import G1TBD.LABTBD.data.point.PointEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "users")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity implements UserDetails {

    @Id
    @Column(unique = true, nullable = false)
    private String rut;

    private String email;
    private String name;
    private String lastname;
    private Date birthdate;
    private String sex;
    private String password;
    private String role;
    private boolean availability;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location", referencedColumnName = "point_id")
    private PointEntity location;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getUsername() {
        return rut;
    }
}
