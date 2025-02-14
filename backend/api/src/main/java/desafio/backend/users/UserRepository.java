package desafio.backend.users;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    // Query method to find a user by email
    User findByEmail(String email);
}
