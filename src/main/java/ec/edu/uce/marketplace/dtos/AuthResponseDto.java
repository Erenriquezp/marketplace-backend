package ec.edu.uce.marketplace.dtos;

public class AuthResponseDto {
    private String accessToken;
    private String role; // Nuevo campo para el rol del usuario

    // Constructor
    public AuthResponseDto() {
    }

    public AuthResponseDto(String accessToken, String role) {
        this.accessToken = accessToken;
        this.role = role;
    }

    // Getters
    public String getAccessToken() {
        return accessToken;
    }

    public String getRole() {
        return role;
    }

    // Setters
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // toString
    @Override
    public String toString() {
        return "AuthResponseDto{" +
                "accessToken='" + accessToken + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
