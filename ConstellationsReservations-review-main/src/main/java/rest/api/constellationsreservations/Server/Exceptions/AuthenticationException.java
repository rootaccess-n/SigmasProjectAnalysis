package rest.api.constellationsreservations.Server.Exceptions;

import lombok.Getter;

@Getter
public class AuthenticationException extends RuntimeException {
    private String message;

public AuthenticationException(String message) {
    super(message);
}

}
