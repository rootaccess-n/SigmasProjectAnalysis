package rest.api.constellationsreservations.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rest.api.constellationsreservations.DB.Repository.TokenRepository;
import rest.api.constellationsreservations.DB.Repository.UsersRepository;
import rest.api.constellationsreservations.DTO.LoginCredentials;
import rest.api.constellationsreservations.Entity.Users;
import rest.api.constellationsreservations.Enums.Role;
import rest.api.constellationsreservations.Enums.Subscription;
import rest.api.constellationsreservations.Server.Exceptions.UserException;

@Service
public class UsersService {

    private static final Logger log = LoggerFactory.getLogger(UsersService.class);
    private final PasswordEncoder passwordEncoder;
    private final UsersRepository usersRepository;
    private final JWTService jwtService;
    private final TokenRepository tokenRepository;

    @Autowired
    public UsersService(PasswordEncoder passwordEncoder, UsersRepository usersRepository, JWTService jwtService, TokenRepository tokenRepository) {
        this.passwordEncoder = passwordEncoder;
        this.usersRepository = usersRepository;
        this.jwtService = jwtService;
        this.tokenRepository = tokenRepository;
    }


    public String getUser(Users user) {

        Users newUser = usersRepository.findById(user.getId())
                .orElseThrow(()->{
                    log.warn("User not found, id: " + usersRepository.findById(user.getId()) + " failed in refresh token");
                    return new UserException("User not found");
                });

        if (newUser!=null) {
            return newUser.getUsername();
        }

        return "User not found";
    }

    public Users createUser(LoginCredentials user) {
        Users newUser = new Users();
        newUser.setUsername(user.username());
        newUser.setPassword(passwordEncoder.encode(user.password()));
        newUser.setRole(Role.USER);
        newUser.setSubscriptionOnType(Subscription.STANDARD);

        try {
            return usersRepository.saveAndFlush(newUser);

        } catch (DataIntegrityViolationException e) {
            log.warn(
                    "Username already exists during registration: {}",
                    user.username()
            );

            throw new UserException("Username already exists");
        }

    }





}










