package tech.kibetimmanuel.identityservice.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserDto {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String password;

}
