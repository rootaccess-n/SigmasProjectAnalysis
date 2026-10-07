package rest.api.constellationsreservations.Config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Component
@Getter
public  class JWTParamaters {
    public final static String SECRET = "dfbcce81d360c6d334a1feb04a641aa66560ff5315d375d9f1ccb55eaaf5db58";
    public static final Duration ACCESS_LIFETIME = Duration.ofMinutes(1);
    public static final Duration REFRESH_LIFETIME = Duration.ofDays(20);
    public final static int GRACE_PERIOD = 5; //sec

}
