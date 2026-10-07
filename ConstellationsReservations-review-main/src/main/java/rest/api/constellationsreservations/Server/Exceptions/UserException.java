package rest.api.constellationsreservations.Server.Exceptions;

import lombok.Getter;

@Getter
public class UserException extends RuntimeException {

    private String message;

    public UserException(String message) {
        super(message);
    }
}
