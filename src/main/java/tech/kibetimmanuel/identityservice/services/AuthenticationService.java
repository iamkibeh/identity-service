package tech.kibetimmanuel.identityservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tech.kibetimmanuel.identityservice.dtos.LoginResponse;
import tech.kibetimmanuel.identityservice.dtos.LoginUserDto;
import tech.kibetimmanuel.identityservice.dtos.RegisterUserDto;
import tech.kibetimmanuel.identityservice.entities.User;
import tech.kibetimmanuel.identityservice.repositories.UserRepo;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepo userRepo;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public User signup(RegisterUserDto userInput) {
        User user = User.builder()
                .firstName(userInput.getFirstName())
                .lastName(userInput.getLastName())
                .phoneNumber(userInput.getPhoneNumber())
                .email(userInput.getEmail())
                .password(passwordEncoder.encode(userInput.getPassword()))
                .build();

        return userRepo.save(user);
    }

    public User authenticate(LoginUserDto userInput) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userInput.getEmail(),
                        userInput.getPassword()
                )
        );
        return userRepo.findByEmail(userInput.getEmail()).orElseThrow();

    }


    public LoginResponse mapUserToDto(User authenticatedUser) {
        return LoginResponse.builder()
                .user(authenticatedUser)
                .token(jwtService.generateToken(authenticatedUser))
                .expiresIn(jwtService.getJwtExpiration())
                .build();
    }
}
