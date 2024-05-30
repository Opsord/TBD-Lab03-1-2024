package G1TBD.LABTBD.auth.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationRequest {
    private Double latitude;
    private Double longitude;
}
