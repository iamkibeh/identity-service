package tech.kibetimmanuel.identityservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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
}
