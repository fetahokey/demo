package prototype.carpooling.dm3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prototype.carpooling.dm3.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

}
