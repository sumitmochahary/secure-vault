package com.securevault.app.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TotpVerificationRequest {

    @NotBlank(message = "Email cannot be empty")
    private String email;

    @NotBlank(message = "OTP cannot be empty")
    private String otp; // OTP from Google Authenticator
}
