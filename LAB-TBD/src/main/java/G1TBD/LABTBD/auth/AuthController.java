package G1TBD.LABTBD.auth;

import G1TBD.LABTBD.auth.entities.AuthResponse;
import G1TBD.LABTBD.auth.entities.LoginRequest;
import G1TBD.LABTBD.auth.entities.RegisterRequest;
import G1TBD.LABTBD.auth.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private static final Logger logger = Logger.getLogger(AuthController.class.getName());

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        logger.info("Registering user: " + request.toString());
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        logger.info("Logging in user: " + request.toString());
        return ResponseEntity.ok(authService.login(request));
    }

}