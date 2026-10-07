package rest.api.constellationsreservations.Server;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import rest.api.constellationsreservations.Server.Exceptions.AuthenticationException;
import rest.api.constellationsreservations.Server.Exceptions.TokenException;
import rest.api.constellationsreservations.Server.Exceptions.UserException;

import java.time.Instant;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<UserException> handleUser(UserException ex) {

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new UserException("User error: "+ex.getMessage()));

    }

    @ExceptionHandler(TokenException.class)
    public ResponseEntity<TokenException> handleToken(TokenException ex) {

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new TokenException("Token error: "+ex.getMessage())
                );

    }


    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<AuthenticationException> handleAuthentication(AuthenticationException ex) {

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new AuthenticationException("Authentication error: "+ex.getMessage())
                );

    }









}