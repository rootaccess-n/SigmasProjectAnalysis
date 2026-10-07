package rest.api.constellationsreservations.Service;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rest.api.constellationsreservations.Config.JWTParamaters;
import rest.api.constellationsreservations.DB.Repository.TokenRepository;
import rest.api.constellationsreservations.DB.Repository.UsersRepository;
import rest.api.constellationsreservations.DTO.LoginCredentials;
import rest.api.constellationsreservations.DTO.TokensDTO;
import rest.api.constellationsreservations.Entity.Token;
import rest.api.constellationsreservations.Entity.Users;
import rest.api.constellationsreservations.Server.Exceptions.TokenException;
import rest.api.constellationsreservations.Server.Exceptions.UserException;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;


@Service
public class AuthService {


    private final PasswordEncoder passwordEncoder;
    private final UsersRepository usersRepository;
    private final JWTService jwtService;
    private final TokenRepository tokenRepository;
    private final UsersService usersService;

    private static final Logger log =
            LoggerFactory.getLogger(AuthService.class);

    @Autowired
    public AuthService(PasswordEncoder passwordEncoder, UsersRepository usersRepository, JWTService jwtService, TokenRepository tokenRepository, UsersService usersService) {
        this.passwordEncoder = passwordEncoder;
        this.usersRepository = usersRepository;
        this.jwtService = jwtService;
        this.tokenRepository = tokenRepository;
        this.usersService = usersService;
    }




    public TokensDTO registration(LoginCredentials loginCredentials){

        Users user = usersService.createUser(loginCredentials);

        return createAndSaveTokens(user);
    }



    public TokensDTO login(LoginCredentials dto) {

        Users user = usersRepository.findByUsername(dto.username())
                .orElseThrow(() -> {
                    log.warn("User not found: " + dto.username() + " failed in login");
                  return new UserException("User not found");
                });

        boolean matches = passwordEncoder.matches( dto.password() , user.getPassword() );

        if (!matches) {
            throw new UserException("Incorrect password");
        }

            return createAndSaveTokens(user);

    }


@Transactional
    public void logout(String refreshToken){
        DecodedJWT decodedJWT = jwtService.verifyRefreshToken(refreshToken);

        Token token = tokenRepository.findByJtiHash(JWTService.hashJti( decodedJWT.getClaim("jti").asString() ))
                .orElseThrow(()->{
                    log.warn("Token not found, jti-hash: " + decodedJWT.getClaim("jti").asString()+" failed in logout");
                   return new TokenException("Token not found");
                });


           tokenRepository.revokeToken( token.getJtiHash(), Instant.now() );

    }

    @Transactional(noRollbackFor = TokenException.class)
    public TokensDTO refreshTokens(String refreshToken){
        Instant now = Instant.now();
       DecodedJWT decodedJWT = jwtService.verifyRefreshToken(refreshToken);

        Token token = tokenRepository.findByJtiHash(JWTService.hashJti( decodedJWT.getClaim("jti").asString()) )
                .orElseThrow(()->{
                    log.warn("Token not found, jti-hash: " + decodedJWT.getClaim("jti").asString()+" failed in refreshTokens");
                    return new TokenException("Token not found");
                });


        if(token.getExpiredAt().isBefore(now)){
            log.warn("Refresh token has expired, jti-hash: " + decodedJWT.getClaim("jti").asString()+" failed in refreshTokens");
            throw new TokenException("Refresh token expired");
        }

        if (!token.isValid()) {
            Instant oldTime = token.getOldRefreshTime();


            if(now.isAfter(oldTime.plusSeconds(JWTParamaters.GRACE_PERIOD))){
                int updated = tokenRepository.revokeFamilyTokens(token.getFamilyID(), now);
                log.warn("Refresh token reuse detected, familyId: "+token.getFamilyID() );
              //  log.info("Revoked tokens count: {}", updated);

                throw new TokenException("Refresh token reuse detected");
            }

            throw new TokenException("Refresh token expired");
        }

        token.setValid(false);
        token.setOldRefreshTime(now);
        tokenRepository.save(token);

        Users userForTokens = usersRepository.findById(token.getUserID())
                .orElseThrow(()->{
                    log.warn("User not found, id: " + token.getUserID() + " failed in refresh token");
                    return new UserException("User not found");
                });

        String access = jwtService.createAccessToken(userForTokens);
        String refresh = jwtService.createRefreshToken(userForTokens);
        DecodedJWT decodeJWT = jwtService.verifyRefreshToken(refresh);


        Token newToken = Token.builder()
                .expiredAt(now.plus(JWTParamaters.REFRESH_LIFETIME))
                .createdAt(now)

                .userID(decodedJWT.getClaim("userID").asLong())
                .jtiHash(JWTService.hashJti( decodeJWT.getClaim("jti").asString() ))
                .valid(true)
                .oldRefreshTime(null)

               //difference between createAndSaveTokens
                .familyID(token.getFamilyID())

                .build();

        tokenRepository.save(newToken);

        TokensDTO tokensDTO = new TokensDTO();

        tokensDTO.setAccessToken(access);
        tokensDTO.setRefreshToken(refresh);

        return tokensDTO;

    }






    private TokensDTO createAndSaveTokens(Users user) {
        Instant now = Instant.now();
        String access = jwtService.createAccessToken(user);
        String refresh = jwtService.createRefreshToken(user);
        DecodedJWT decodedJWT = jwtService.verifyRefreshToken(refresh);

        Token token = Token.builder()
                .expiredAt(now.plus(JWTParamaters.REFRESH_LIFETIME))
                .createdAt(now)

                .userID(decodedJWT.getClaim("userID").asLong())
                .jtiHash(JWTService.hashJti( decodedJWT.getClaim("jti").asString() ))
                .valid(true)

                .familyID(decodedJWT.getClaim("familyID").asString())
                .oldRefreshTime(null)

                .build();

        tokenRepository.save(token);

        TokensDTO tokensDTO = new TokensDTO();

        tokensDTO.setAccessToken(access);
        tokensDTO.setRefreshToken(refresh);

        return tokensDTO;
    }






}


