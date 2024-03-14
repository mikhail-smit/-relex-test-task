package mikhail.task.controllers;

import mikhail.task.dto.AuthDTO;
import mikhail.task.dto.JwtDTO;
import mikhail.task.services.WorkerDetailsService;
import mikhail.task.utils.JwtUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final WorkerDetailsService workerDetailsService;
    private final JwtUtils jwtUtils;

    public AuthController(AuthenticationManager authManager, WorkerDetailsService workerDetailsService, JwtUtils jwtUtils) {
        this.authManager = authManager;
        this.workerDetailsService = workerDetailsService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping
    public ResponseEntity<JwtDTO> auth(@RequestBody AuthDTO auth) {
        UserDetails userDetails = (UserDetails) authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        auth.getEmail(),
                        auth.getPassword()
                )).getPrincipal();

        return ResponseEntity.ok(new JwtDTO(jwtUtils.getToken(userDetails)));
    }

}
