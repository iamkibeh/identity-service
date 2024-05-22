package tech.kibetimmanuel.identityservice.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.kibetimmanuel.identityservice.dtos.LoginResponse;
import tech.kibetimmanuel.identityservice.dtos.LoginUserDto;
import tech.kibetimmanuel.identityservice.dtos.RegisterUserDto;
import tech.kibetimmanuel.identityservice.entities.User;
import tech.kibetimmanuel.identityservice.services.AuthenticationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {
    private  final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterUserDto registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);
        return new ResponseEntity<>(registeredUser, HttpStatus.ACCEPTED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginUserDto credentials) {
        User authenticatedUser = authenticationService.authenticate(credentials);
        var response = authenticationService.mapUserToDto(authenticatedUser);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
