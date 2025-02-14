package desafio.backend.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import desafio.backend.users.User;
import desafio.backend.users.UserFacade;

@RestController
@RequestMapping("/auth")
public class Login {

    @Autowired
    private UserFacade userFacade;

    @Autowired
    private JwtUtil jwtUtil;  // Use the injected JwtUtil instance

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String senha) {

        // Fetch user from the database using the email
        User user = userFacade.findByEmail(email);

        if (user == null) {
            return ResponseEntity.status(401).body("User not found");
        }

        // Check if the password matches
        if (user.getSenha().equals(senha)) {  // Direct comparison (consider hashing passwords in production)
            String token = jwtUtil.generateToken(email);  // Use the injected JwtUtil instance to generate the token
            return ResponseEntity.ok().header("Authorization", "Bearer " + token).body("Login successful");
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
}
