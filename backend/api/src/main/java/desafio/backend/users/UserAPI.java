package desafio.backend.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserAPI {
    @Autowired
    private UserFacade userFacade;

    @PostMapping
    @ResponseBody
    public UserDTO criar(@RequestBody UserDTO userDTO) {
        return userFacade.criar(userDTO);
    }

    @PutMapping("/{userId}")
    @ResponseBody
    public UserDTO atualizar(@PathVariable("userId") Long userId,
                             @RequestBody UserDTO userDTO) {
        return userFacade.atualizar(userDTO, userId);
    }

    @GetMapping
    @ResponseBody
    public List<UserDTO> getAll() {
        return userFacade.getAll();
    }

    @DeleteMapping("/{userId}")
    @ResponseBody
    public String deletar(@PathVariable("userId") Long userId) {
        return userFacade.delete(userId);
    }
}
