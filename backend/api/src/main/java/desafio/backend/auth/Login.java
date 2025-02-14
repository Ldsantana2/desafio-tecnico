package desafio.backend.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import desafio.backend.users.User;
import desafio.backend.users.UserFacade;

@CrossOrigin(origins = "http://localhost:3000", exposedHeaders = "Authorization")
@RestController
@RequestMapping("/auth")
public class Login {

    @Autowired
    private UserFacade userFacade;

    @Autowired
    private JwtUtil jwtUtil;  

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        // Buscar usuário no banco pelo e-mail
        User user = userFacade.findByEmail(loginRequest.getEmail());

        if (user == null) {
            return ResponseEntity.status(401).body("User not found");
        }

        // Verificar se a senha está correta
        if (user.getSenha().equals(loginRequest.getSenha())) {
            String token = jwtUtil.generateToken(loginRequest.getEmail());
            return ResponseEntity.ok()
                    .header("Authorization", "Bearer " + token)
                    .body("Login successful");
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    } // <- FECHAMENTO DO MÉTODO `login`

} // <- FECHAMENTO DA CLASSE `Login`
