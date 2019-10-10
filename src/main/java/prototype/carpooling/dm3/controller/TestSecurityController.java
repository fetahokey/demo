package prototype.carpooling.dm3.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestSecurityController {

    @GetMapping("/home")
    public String publicApi(){
        return "This is public API || {This is home page}";
    }

    @GetMapping("/secured")
    @Secured("ROLE_ADMIN")
    public String securedApi(){
        return "This is secured API || ADMIN_ROLE";
    }

    @GetMapping("/manager")
    @Secured("ROLE_MANAGER")
    public String securedApi2(){
        return "This is secured API || MANAGER_ROLE";
    }
}
