package com.securevault.app.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterRequest {

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 3, message = "Name must be at least 3 characters")
    // This regex strictly allows only letters (a-z, A-Z) and spaces.
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Name can only contain letters and spaces")
    private String name;

    @NotNull(message = "Phone number cannot be blank")
    // This regex is for 10-digit mobile numbers (starts with 6, 7, 8, or 9)
    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Please enter a valid 10-digit phone number")
    private Long number;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Please enter a valid email address")
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{8,}$",
            message = "Password must contain at least one digit, one lowercase letter, one uppercase letter, one special character (!@#$%^&+=), and no whitespace."
    )
    private String password;
}
