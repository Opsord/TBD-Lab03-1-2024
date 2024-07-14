package G1TBD.LABTBD.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SingleEmergencyData {

    private String emergencyTitle;//Titulo de la emergencia
    private int volunteerQuantity;//Cantidad de voluntarios
    private int taskQuantity;//Cantidad de tareas
}
