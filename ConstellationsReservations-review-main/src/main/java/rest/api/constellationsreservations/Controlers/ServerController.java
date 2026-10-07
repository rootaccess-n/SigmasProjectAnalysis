package rest.api.constellationsreservations.Controlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rest.api.constellationsreservations.DTO.LoginCredentials;
import rest.api.constellationsreservations.DTO.TokensDTO;
import rest.api.constellationsreservations.Server.ServerResponse;
import rest.api.constellationsreservations.Service.AuthService;

import java.time.Duration;

@RestController
public class ServerController {


    private final AuthService serverService;

    @Autowired
    public ServerController(  AuthService serverService) {
        this.serverService = serverService;
    }


    @PostMapping("/registration")
    public ResponseEntity<ServerResponse<String>> createUser(@RequestBody LoginCredentials loginDTO) {

        TokensDTO tokensDTO = serverService.registration(loginDTO);

        ResponseCookie cookie = ResponseCookie.from("refreshToken", tokensDTO.getRefreshToken() )
                .httpOnly(true)
                .sameSite("Lax")
                .path("/")
                .maxAge(Duration.ofDays(20))
                .build();


        return  ResponseEntity
                .status(HttpStatus.CREATED)
                .header(HttpHeaders.SET_COOKIE,cookie.toString())
                .body(ServerResponse.success( "Created new user","access=" + tokensDTO.getAccessToken() )  );
    }


    @PostMapping("/login")
    public ResponseEntity<ServerResponse<String>> login(@RequestBody LoginCredentials dto) {

   TokensDTO tokensDTO = serverService.login(dto);

        ResponseCookie cookie = ResponseCookie.from("refreshToken", tokensDTO.getRefreshToken() )
                .httpOnly(true)
                .sameSite("Lax")
                .path("/api")
                .maxAge(Duration.ofDays(20))
                .build();

        return  ResponseEntity.status(HttpStatus.CREATED)
                .header(HttpHeaders.SET_COOKIE,cookie.toString())
                .body(ServerResponse.success( "Login successfully","access=" + tokensDTO.getAccessToken() )  );
    }


    @GetMapping("/logouts")
    public ResponseEntity<ServerResponse<String>> logout(@CookieValue("refreshToken") String refresh) {

        serverService.logout(refresh);

        return ResponseEntity.ok(ServerResponse.success("Logout successfully"));
    }


    @GetMapping("/refresh")
    public ResponseEntity<ServerResponse<TokensDTO>> refreshToken(@CookieValue("refreshToken") String refresh) {

        TokensDTO tokensDTO = serverService.refreshTokens(refresh);

        return ResponseEntity.ok(ServerResponse.success(tokensDTO));
    }





    @GetMapping("/test")
    public String test() {

        return "OK";
    }





}














