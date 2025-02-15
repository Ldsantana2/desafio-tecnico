package desafio.backend.apiexterna;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import desafio.backend.users.UserDTO;

@Service
public class Conectar {
    private static final String API_URL = "https://jsonplaceholder.typicode.com/users/";
    private final RestTemplate restTemplate = new RestTemplate();

public UserDTO fetchUserData(Long userId) {
    String url = API_URL + userId;
    System.out.println("Fetching external data from: " + url);

    try {
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        UserDTO userDTO = objectMapper.readValue(response.getBody(), UserDTO.class);

        return userDTO;
    } catch (Exception e) {
        System.err.println("Error fetching user data: " + e.getMessage());
        return null;
    }
}
}


