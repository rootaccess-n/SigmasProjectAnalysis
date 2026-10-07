package rest.api.constellationsreservations.Security;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import rest.api.constellationsreservations.DB.Repository.UsersRepository;
import rest.api.constellationsreservations.Entity.Users;
import rest.api.constellationsreservations.Server.Exceptions.UserException;
import rest.api.constellationsreservations.Service.AuthService;
@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsersRepository userRepository;


    @Autowired
    public CustomUserDetailsService(UsersRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Users user = (Users) userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.warn("User not found in CustomUserDetailsService");
                    return new UserException("User not found ");
                });

        return new CustomUserDetails(user);
    }
}
