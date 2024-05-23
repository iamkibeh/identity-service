package tech.kibetimmanuel.identityservice.dtos;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserDto {
    @NotEmpty(message = "First name cannot be empty" )
    @NotBlank(message = "First name cannot be blank" )
    private String firstName;
    @NotEmpty(message = "Last name cannot be empty" )
    @NotBlank(message = "Last name cannot be blank" )
    private String lastName;
    @NotEmpty(message = "Phone number name cannot be empty" )
    @NotBlank(message = "Phone number name cannot be blank" )
    private String phoneNumber;
    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    @NotEmpty(message = "Email cannot be empty")
    private String email;
    @NotEmpty(message = "Password cannot be empty" )
    @NotBlank(message = "Password cannot be blank" )
    @Length(min = 6,  message = "password length must be at least 6 characters long")
    private String password;

}
