package prototype.carpooling.dm3.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import prototype.carpooling.dm3.model.Role;
import prototype.carpooling.dm3.model.User;
import prototype.carpooling.dm3.repository.RoleRepository;
import prototype.carpooling.dm3.repository.UserRepository;
import javax.management.relation.RoleNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
public class TestRestController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @PostMapping("/register")
    public User register(@RequestBody User user) throws RoleNotFoundException {
        List<Role> roles = user.getRoles();
        List<Role> newRoles = new ArrayList<Role>();
        for (Role role : roles) {
            if (role.getId() == (null)) {
                newRoles.add(roleRepository.save(role));
            } else {
                Optional<Role> optionalRole = roleRepository.findById(role.getId());
                optionalRole.orElseThrow(() -> new RoleNotFoundException("Role not found"));
                newRoles.add(optionalRole.get());
            }
        }
        user.setRoles(newRoles);
        return userRepository.save(user);
    }
}