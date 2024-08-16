package G1TBD.LABTBD.auth.services;

import G1TBD.LABTBD.auth.entities.AuthResponse;
import G1TBD.LABTBD.auth.entities.LoginRequest;
import G1TBD.LABTBD.auth.entities.RegisterRequest;
import G1TBD.LABTBD.config.JwtService;
import G1TBD.LABTBD.data.point.PointEntity;
import G1TBD.LABTBD.data.point.PointService;
import G1TBD.LABTBD.app.user.services.UserPointService;
import G1TBD.LABTBD.mongo.user.models.UserMongo;
import G1TBD.LABTBD.mongo.user.models.UserRole;
import G1TBD.LABTBD.mongo.user.services.UserMongoService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class AuthServiceImp implements AuthService{

    private final PointService pointService;
    private final UserPointService userPointService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserMongoService userMongoService;

    private static final Logger logger = Logger.getLogger(AuthServiceImp.class.getName());

    @Override
    public AuthResponse register(RegisterRequest request) {
        var locationRequest = request.getLocation();

        // Create UserMongo
        var user = UserMongo.builder()
                .rut(request.getRut())
                .email(request.getEmail())
                .name(request.getName())
                .last_name(request.getLast_name())
                .birth_date(request.getBirth_date())
                .sex(request.getSex())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(UserRole.valueOf(request.getRole()))
                .availability(request.isAvailability())
                .skills(request.getSkills())
                .build();
        userMongoService.saveUser(user);

        if (locationRequest != null) {
            // Crear y guardar el punto
            var point = PointEntity.builder()
                    .latitude(locationRequest.getLatitude())
                    .longitude(locationRequest.getLongitude())
                    .build();

            // Guarda el punto y recibe el objeto con el ID asignado
            Long pointId = pointService.create(point);

            // Crea la relación entre el usuario y el punto usando el ID asignado
            userPointService.create(user.getRut(), pointId);
        }

        var jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder().token(jwtToken).build();
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getRut(), request.getPassword())
        );

        var user = userMongoService.getUserByRut(request.getRut()).orElseThrow();
        logger.info("User logged in: " + user.toString());

        var jwtToken = jwtService.generateToken(user);
        logger.info("Token generated: " + jwtToken);
        return AuthResponse.builder().token(jwtToken).build();
    }

}
    
