package prototype.carpooling.dm3.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import prototype.carpooling.dm3.model.CustomUserDetail;
import prototype.carpooling.dm3.model.User;
import prototype.carpooling.dm3.repository.UserRepository;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private UserRepository userRepository;


    public JwtAuthorizationFilter(AuthenticationManager authenticationManager,  UserRepository userRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // Read the authorization header where the JWT token should be
        String header = request.getHeader(JwtProperties.HEADER_STRING);

        // if header does not contains Bearer or is null delegate to spring impl and exit
        if (header == null || !header.startsWith(JwtProperties.TOKEN_PREFIX)){
            chain.doFilter(request, response);
            return;
        }

        // if header is present , try grab user principal from database and perform authorization
        Authentication authentication = getUsernamePasswordAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Continue filter execution
        chain.doFilter(request, response);
    }

    private Authentication getUsernamePasswordAuthentication(HttpServletRequest request) {
        String token =  request.getHeader(JwtProperties.HEADER_STRING)
                               .replace(JwtProperties.TOKEN_PREFIX,"");
        if (token!=null){
            //parse the token and validate it
            String userName = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET.getBytes()))
                .build()
                .verify(token)
                .getSubject();




            // search in th database if we found the user by token subject (username)
            // then grab user details and create spring auth token using username , pass, roles
            if(userName != null){
                Optional<User> userOpt = userRepository.findByUsername(userName);
                User user = userOpt.map(User::new).orElseThrow(() -> new UsernameNotFoundException("User not found !"));
                CustomUserDetail userPrincipal = new CustomUserDetail(user);
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                  userName, null, userPrincipal.getAuthorities()
                );
                return auth;
            }
        }
        return null;
    }


}
