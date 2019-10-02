package prototype.carpooling.dm3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import prototype.carpooling.dm3.model.CustomUserDetail;
import prototype.carpooling.dm3.model.User;
import prototype.carpooling.dm3.repository.UserRepository;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        optionalUser.orElseThrow(() -> new UsernameNotFoundException("User not found !"));
        CustomUserDetail customUserDetail = optionalUser.map(user -> {return new CustomUserDetail(user);}).get();
//        CustomUserDetail customUserDetail2 = optionalUser.map(CustomUserDetail::new).get(); // Lambda
        return customUserDetail;
    }
}
