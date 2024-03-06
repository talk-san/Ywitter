package com.talxan.ywitter.auth;

import com.talxan.ywitter.yuser.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "First name cannot be empty")
    @Size(min=2, max=30)
    private String firstName;

    @NotBlank(message = "First name cannot be empty")
    @Size(min=2, max=30)
    private String lastName;

    @Email(message = "Invalid email format")
    private String email;

//    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$@!%&*?])[A-Za-z\\d#$@!%&*?]{8,}$",
//            message = "Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character")
    private String password;

    private Role role = Role.USER;
}
