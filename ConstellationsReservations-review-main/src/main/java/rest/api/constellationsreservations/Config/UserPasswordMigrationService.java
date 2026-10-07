//package rest.api.constellationsreservations.Config;
//
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import rest.api.constellationsreservations.DB.Repository.UsersRepository;
//import rest.api.constellationsreservations.Entity.Users;
//
//import java.util.List;
//
//@Service
//public class UserPasswordMigrationService {
//
//    private final UsersRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    public UserPasswordMigrationService(UsersRepository userRepository,
//                                        PasswordEncoder passwordEncoder) {
//        this.userRepository = userRepository;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    @Transactional
//    public void encryptAllPlainPasswords() {
//        List<Users> users = userRepository.findAll();
//
//        for (Users user : users) {
//            String plainPassword = user.getPassword();
//
//            // Пропускаем, если уже выглядит как BCrypt
//            if (plainPassword != null && !plainPassword.startsWith("$2a$")) {
//                String encoded = passwordEncoder.encode(plainPassword);
//                user.setPassword(encoded);
//            }
//        }
//
//        userRepository.saveAll(users);
//    }
//}
