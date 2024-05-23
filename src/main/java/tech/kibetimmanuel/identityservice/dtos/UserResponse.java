package tech.kibetimmanuel.identityservice.dtos;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String fullName;
    private String email;
    private String phoneNumber;
    private boolean enabled;
    private Collection<? extends GrantedAuthority> roles;
    private Date createdAt;
    private Date updatedAt;
}
