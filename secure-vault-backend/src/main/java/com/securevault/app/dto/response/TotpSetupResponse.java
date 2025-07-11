package com.securevault.app.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TotpSetupResponse {
    private String qrCodeUrl; // For scanning Google Authenticator
    private String secret;
}
