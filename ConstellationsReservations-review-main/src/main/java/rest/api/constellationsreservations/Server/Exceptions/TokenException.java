package rest.api.constellationsreservations.Server.Exceptions;

import lombok.Getter;

@Getter
public class TokenException extends RuntimeException {
    private String message;

    public TokenException(String message) {
        super(message);
    }

}
