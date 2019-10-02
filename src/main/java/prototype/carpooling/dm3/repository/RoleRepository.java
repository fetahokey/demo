package prototype.carpooling.dm3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prototype.carpooling.dm3.model.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
