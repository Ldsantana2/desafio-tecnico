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
    
        repository.save(user);  
    
        UserDTO externalUserData = conectar.fetchUserData(user.getId());  
    
        user.setNome(externalUserData.getNome());
        user.setTelefone(externalUserData.getTelefone());
        user.setWebsite(externalUserData.getWebsite());
        user.setEndereco(externalUserData.getEndereco());
        user.setEmpresa(externalUserData.getEmpresa());
    
        repository.save(user);  
    
   
        User updatedUser = repository.findById(user.getId()).orElse(null);
    
        if (updatedUser == null) {
            System.err.println("Error: Updated user not found in the database.");
            return userDTO;  
        }
    
        
        return new UserDTO(updatedUser);
    }

    public UserDTO atualizar(UserDTO userDTO, Long userId) {
        
        User userDatabase = repository.getReferenceById(userId);
        
        if (userDTO.getEmail() != null && !userDTO.getEmail().equals(userDatabase.getEmail())) {
            userDatabase.setEmail(userDTO.getEmail());
        }
        
        if (userDTO.getSenha() != null && !userDTO.getSenha().equals(userDatabase.getSenha())) {
            userDatabase.setSenha(userDTO.getSenha());
        }
    
       
        repository.save(userDatabase);
        
        UserDTO updatedUserDTO = new UserDTO();
        updatedUserDTO.setId(userDatabase.getId());
        
        return updatedUserDTO;
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

    public User findByEmail(String email) {
        return repository.findByEmail(email);
    }
}
