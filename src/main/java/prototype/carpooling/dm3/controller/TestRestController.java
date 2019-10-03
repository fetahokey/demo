package prototype.carpooling.dm3.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import prototype.carpooling.dm3.model.User;
import prototype.carpooling.dm3.service.UserService;

@RestController
@Slf4j
public class TestRestController {
    private final UserService userService;

    public TestRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        if (user.getId() != null) {
            throw new IllegalStateException("A new user cannot already have an ID");
        }
        return userService.register(user);
    }
}