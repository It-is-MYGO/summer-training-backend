package modules.auth.service;

import modules.auth.dto.AuthResponse;
import modules.auth.dto.LoginRequest;
import modules.auth.dto.RegisterRequest;
import org.springframework.security.core.Authentication;

public interface AuthService {
    AuthResponse authenticateUser(LoginRequest loginRequest);
    AuthResponse registerUser(RegisterRequest registerRequest);
    String generateJwtToken(Authentication authentication);
}