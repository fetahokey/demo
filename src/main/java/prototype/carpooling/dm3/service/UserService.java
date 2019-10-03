package prototype.carpooling.dm3.service;

import org.springframework.stereotype.Service;
import prototype.carpooling.dm3.model.Role;
import prototype.carpooling.dm3.model.User;
import prototype.carpooling.dm3.repository.RoleRepository;
import prototype.carpooling.dm3.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public User register(User user) {
        List<Role> roles = user.getRoles().stream()
                .map(roleRepository::save)
                .collect(Collectors.toList());
        user.setRoles(roles);
        user = userRepository.save(user);
        return user;
    }
}
