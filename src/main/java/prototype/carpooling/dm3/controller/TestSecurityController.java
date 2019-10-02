package prototype.carpooling.dm3.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestSecurityController {

    @GetMapping("/home")
    public String publicApi(){
        return "This is public API {This is home page}";
    }

    @GetMapping("/secured")
    public String securedApi(){
        return "This is secured API";
    }
}
