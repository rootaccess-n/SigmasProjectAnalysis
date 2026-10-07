package rest.api.constellationsreservations.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor @NoArgsConstructor
public class TokensDTO {

    private String accessToken;
    private String refreshToken;

}
