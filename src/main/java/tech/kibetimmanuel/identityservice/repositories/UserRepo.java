package tech.kibetimmanuel.identityservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.kibetimmanuel.identityservice.entities.User;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
