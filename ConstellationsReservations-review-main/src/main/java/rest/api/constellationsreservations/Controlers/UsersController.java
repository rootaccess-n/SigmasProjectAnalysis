package rest.api.constellationsreservations.Controlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import rest.api.constellationsreservations.Entity.Users;
import rest.api.constellationsreservations.Security.CustomUserDetails;
import rest.api.constellationsreservations.Server.ServerResponse;
import rest.api.constellationsreservations.Service.UsersService;

@RestController
@RequestMapping("/User")
public class UsersController {

    private final UsersService usersService;

    @Autowired
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public ResponseEntity<ServerResponse<String>> getUser(@AuthenticationPrincipal CustomUserDetails customUser) {
        Users user = customUser.getUser();
        String message = usersService.getUser(user);
     return new ResponseEntity<>(ServerResponse.success(message),HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public ResponseEntity<ServerResponse<String>> getAdmin(@AuthenticationPrincipal CustomUserDetails customUser) {
        Users user = customUser.getUser();
        String message = usersService.getUser(user);
        return new ResponseEntity<>(ServerResponse.success(message),HttpStatus.ACCEPTED);
    }




}
