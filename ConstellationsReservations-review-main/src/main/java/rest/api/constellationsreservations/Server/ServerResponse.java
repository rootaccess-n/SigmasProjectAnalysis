package rest.api.constellationsreservations.Server;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor @NoArgsConstructor
@Getter
public class ServerResponse<T> {
    private String message;
    private T data;
    private boolean success;


public static <T> ServerResponse<T> success(T data) {
return new ServerResponse<T>("success", data, true);
}

public static <T> ServerResponse<T> success(String text, T data) {
        return new ServerResponse<T>(text, data, true);
    }

public static <T> ServerResponse<T> fail(String message) {
    return new ServerResponse<T>(message, null, false);
}




}
