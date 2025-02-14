package desafio.backend.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import desafio.backend.auth.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserAPI {
    @Autowired
    private UserFacade userFacade;

    @Autowired
    private JwtUtil jwtUtil;

    public UserAPI(UserFacade userFacade, JwtUtil jwtUtil) {
        this.userFacade = userFacade;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<?> criar(@RequestBody UserDTO userDTO, HttpServletRequest request) {
        if (!isTokenValid(request)) {
            return ResponseEntity.status(401).body("Unauthorized: Invalid or missing token");
        }
        return ResponseEntity.ok(userFacade.criar(userDTO));
    }

    @PutMapping("/{userId}")
    @ResponseBody
    public ResponseEntity<?> atualizar(@PathVariable("userId") Long userId,
                                       @RequestBody UserDTO userDTO, HttpServletRequest request) {
        if (!isTokenValid(request)) {
            return ResponseEntity.status(401).body("Unauthorized: Invalid or missing token");
        }
        return ResponseEntity.ok(userFacade.atualizar(userDTO, userId));
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<?> getAll(HttpServletRequest request) {
        if (!isTokenValid(request)) {
            return ResponseEntity.status(401).body("Unauthorized: Invalid or missing token");
        }
        return ResponseEntity.ok(userFacade.getAll());
    }

    @DeleteMapping("/{userId}")
    @ResponseBody
    public ResponseEntity<?> deletar(@PathVariable("userId") Long userId, HttpServletRequest request) {
        if (!isTokenValid(request)) {
            return ResponseEntity.status(401).body("Unauthorized: Invalid or missing token");
        }
        return ResponseEntity.ok(userFacade.delete(userId));
    }

    private boolean isTokenValid(HttpServletRequest request) {
        String token = extractToken(request);
        return token != null && jwtUtil.validateToken(token);
    }

    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }
}
