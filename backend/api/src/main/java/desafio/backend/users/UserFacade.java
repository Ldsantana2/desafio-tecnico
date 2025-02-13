package desafio.backend.users;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import desafio.backend.apiexterna.Conectar;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserFacade {
    @Autowired
    private UserRepository repository;

    @Autowired
    private Conectar conectar;

    public UserDTO criar(UserDTO userDTO) {
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setSenha(userDTO.getSenha());
    
        repository.save(user);  // Save first to generate ID
    
        // Fetch additional user data using the newly created user ID
        UserDTO externalUserData = conectar.fetchUserData(user.getId());
    
            user.setNome(externalUserData.getNome());
            user.setTelefone(externalUserData.getTelefone());
            user.setWebsite(externalUserData.getWebsite());
            user.setEndereco(externalUserData.getEndereco());
            user.setEmpresa(externalUserData.getEmpresa());
    
            repository.save(user);  
    
        // Fetch the updated user from the database
        User updatedUser = repository.findById(user.getId()).orElse(null);
    
        if (updatedUser == null) {
            System.err.println("Error: Updated user not found in the database.");
            return userDTO;  // Return at least the original user data
        }
    
        // Return the fully updated user
        return new UserDTO(updatedUser);
    }

    public UserDTO atualizar(UserDTO userDTO, Long userId) {
        User userDatabase = repository.getReferenceById(userId);
        userDatabase.setEmail(userDTO.getEmail());
        userDatabase.setNome(userDTO.getNome());
        userDatabase.setTelefone(userDTO.getTelefone());
        userDatabase.setWebsite(userDTO.getWebsite());
        userDatabase.setEndereco(userDTO.getEndereco());
        userDatabase.setEmpresa(userDTO.getEmpresa());

        repository.save(userDatabase);
        return userDTO;
    }

    private UserDTO converter(User user) {
        UserDTO result = new UserDTO();
        result.setId(user.getId());
        result.setEmail(user.getEmail());
        result.setNome(user.getNome());
        result.setTelefone(user.getTelefone());
        result.setWebsite(user.getWebsite());
        result.setEndereco(user.getEndereco());
        result.setEmpresa(user.getEmpresa());
        return result;
    }

    public List<UserDTO> getAll() {
        return repository
                .findAll()
                .stream()
                .map(this::converter)
                .collect(Collectors.toList());
    }

    public String delete(Long userId) {
        repository.deleteById(userId);
        return "DELETED";
    }
}
