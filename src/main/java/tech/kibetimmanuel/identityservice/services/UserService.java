package tech.kibetimmanuel.identityservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.kibetimmanuel.identityservice.dtos.UserResponse;
import tech.kibetimmanuel.identityservice.entities.User;
import tech.kibetimmanuel.identityservice.repositories.UserRepo;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepo userRepo;

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public UserResponse mapUserToResponse(User currentUser) {
        return UserResponse.builder()
                .id(currentUser.getId())
                .firstName(currentUser.getFirstName())
                .lastName(currentUser.getLastName())
                .fullName(currentUser.getFullName())
                .email(currentUser.getEmail())
                .phoneNumber(currentUser.getPhoneNumber())
                .roles(currentUser.getAuthorities())
                .enabled(currentUser.isEnabled())
                .createdAt(currentUser.getCreatedAt())
                .updatedAt(currentUser.getUpdatedAt())
                .build();
    }
}
