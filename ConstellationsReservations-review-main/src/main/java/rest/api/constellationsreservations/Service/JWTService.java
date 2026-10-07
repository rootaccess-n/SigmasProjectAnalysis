package rest.api.constellationsreservations.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;
import rest.api.constellationsreservations.Config.JWTParamaters;

import rest.api.constellationsreservations.Entity.Users;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.HexFormat;
import java.util.UUID;


@Service
public class JWTService {

    private final Algorithm algorithm =
            Algorithm.HMAC256(JWTParamaters.SECRET);

    public String createAccessToken(Users users) {
        Instant now = Instant.now();
      return  JWT.create()
              .withSubject(users.getUsername())
                .withClaim("userID", users.getId() )
                .withAudience("frontend")
                .withIssuer("my-app")
                .withClaim("roles", users.getRole().name() )
                .withClaim("tokenType", "access_token")
                .withIssuedAt(now)
                .withExpiresAt(now.plus(JWTParamaters.ACCESS_LIFETIME))

                .sign(algorithm);
    }


    public String createRefreshToken(Users users) {
        String jti = UUID.randomUUID().toString();
        String familyID = UUID.randomUUID().toString();
         Instant now = Instant.now();


        String jwt = JWT.create()
                .withAudience("frontend")
                .withClaim("userID", users.getId() )
                .withClaim("jti",jti )
                .withClaim("familyID", familyID )
                .withIssuer("my-app")
                .withClaim("tokenType", "refresh_token")
                .withIssuedAt(now)
                .withExpiresAt(now.plus(JWTParamaters.REFRESH_LIFETIME))


                .sign(algorithm);

         return jwt;
    }




    public DecodedJWT verifyAccessToken(String accessToken) {

        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("my-app")
                .withAudience("frontend")
                .withClaim("tokenType", "access_token")

                .build();

        return verifier.verify(accessToken);

    }


    public DecodedJWT verifyRefreshToken(String refreshToken) {

        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("my-app")
                .withAudience("frontend")
                .withClaim("tokenType", "refresh_token")

                .build();

      return verifier.verify(refreshToken);

    }



    public static String hashJti(String token) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(token.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}



















