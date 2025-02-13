package desafio.backend.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserFacade {
    @Autowired
    private UserRepository repository;

    public UserDTO criar(UserDTO userDTO) {
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setSenha(userDTO.getSenha());
        repository.save(user);
        userDTO.setId(user.getId());
        return userDTO;
    }

    public UserDTO atualizar(UserDTO userDTO, Long userId) {
        User userDatabase = repository.getOne(userId);
        userDatabase.setEmail(userDTO.getEmail());
        userDatabase.setSenha(userDTO.getSenha());
        repository.save(userDatabase);
        return userDTO;
    }

    private UserDTO converter(User user) {
        UserDTO result = new UserDTO();
        result.setId(user.getId());
        result.setEmail(user.getEmail());
        result.setSenha(user.getSenha());
        return result;
    }

    public List<UserDTO> getAll() {
        return repository.findAll().stream().map(this::converter).collect(Collectors.toList());
    }

    public String delete(Long userId) {
        repository.deleteById(userId);
        return "DELETED";
    }
}
