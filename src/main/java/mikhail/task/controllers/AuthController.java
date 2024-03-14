package mikhail.task.controllers;

import lombok.RequiredArgsConstructor;
import mikhail.task.dto.AuthDTO;
import mikhail.task.dto.JwtDTO;
import mikhail.task.services.RoleService;
import mikhail.task.services.UserService;
import mikhail.task.services.WorkerDetailsService;
import mikhail.task.utils.JwtUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authManager;
    private final WorkerDetailsService workerDetailsService;
    private final JwtUtils jwtUtils;
    private final UserService userService;
    private final RoleService roleService;

    @PostMapping
    public ResponseEntity<JwtDTO> auth(@RequestBody AuthDTO auth) {
        UserDetails userDetails = (UserDetails) authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        auth.getEmail(),
                        auth.getPassword()
                )).getPrincipal();

        return ResponseEntity.ok(new JwtDTO(jwtUtils.getToken(userDetails)));
    }

    @PostMapping("/admin/{id}")
    public void makeUserAdmin(@PathVariable int id) {
        userService.addRole(id, roleService.getByName("ROLE_ADMIN"));
    }
}
