package G1TBD.LABTBD.auth.services;

import G1TBD.LABTBD.auth.entities.AuthResponse;
import G1TBD.LABTBD.auth.entities.LoginRequest;
import G1TBD.LABTBD.auth.entities.RegisterRequest;
import G1TBD.LABTBD.config.JwtService;
import G1TBD.LABTBD.data.point.PointEntity;
import G1TBD.LABTBD.data.point.PointService;
import G1TBD.LABTBD.entities.UserEntity;
import G1TBD.LABTBD.services.UserPointService;
import G1TBD.LABTBD.services.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class AuthServiceImp implements AuthService{

    private static final Logger logger = Logger.getLogger(AuthServiceImp.class.getName());

    private final UserService userService;
    private final PointService pointService;
    private final UserPointService userPointService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse register(RegisterRequest request) {
        var locationRequest = request.getLocation();

        // Create user
        var user = UserEntity.builder()
                .rut(request.getRut())
                .email(request.getEmail())
                .name(request.getName())
                .last_name(request.getLast_name())
                .birth_date(request.getBirth_date())
                .sex(request.getSex())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .availability(request.isAvailability())
                .build();
        userService.create(user);

        // Create point
        var point = PointEntity.builder()
                .latitude(locationRequest.getLatitude())
                .longitude(locationRequest.getLongitude())
                .build();
        pointService.create(point);

        // Create relation between user and point
        userPointService.create(user.getRut(), point.getPoint_id());

        var jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder().token(jwtToken).build();
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getRut(), request.getPassword())
        );

        var user = userService.getByRut(request.getRut());
        logger.info("User logged in: " + user.toString());

        var jwtToken = jwtService.generateToken(user);
        logger.info("Token generated: " + jwtToken);
        return AuthResponse.builder().token(jwtToken).build();
    }

}
