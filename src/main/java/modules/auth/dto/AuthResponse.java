package modules.auth.dto;

import lombok.Data;

@Data
public class AuthResponse {
    private String token;
    private UserDto user;
    
    public AuthResponse(String token, UserDto user) {
        this.token = token;
        this.user = user;
    }
    
    @Data
    public static class UserDto {
        private Long id;
        private String username;
        private String email;
        private String registerDate;
        
        public UserDto(Long id, String username, String email, String registerDate) {
            this.id = id;
            this.username = username;
            this.email = email;
            this.registerDate = registerDate;
        }
    }
}