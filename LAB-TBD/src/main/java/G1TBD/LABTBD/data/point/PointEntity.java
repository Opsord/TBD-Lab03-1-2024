package G1TBD.LABTBD.data.point;

import com.bedatadriven.jackson.datatype.jts.serialization.GeometryDeserializer;
import com.bedatadriven.jackson.datatype.jts.serialization.GeometrySerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import java.io.Serializable;

@Data
@Entity
@Table(name = "points")
@NoArgsConstructor
@AllArgsConstructor
public class PointEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long point_id;

    private Double latitude;
    private Double longitude;

    @JsonSerialize(using = GeometrySerializer.class)
    @JsonDeserialize(using = GeometryDeserializer.class)
    @Column(name = "geom", columnDefinition = "geometry(Point, 4326)")
    private Point point;



}