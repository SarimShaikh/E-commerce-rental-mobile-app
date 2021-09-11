package com.ecommerce.app.messageDto;

public class LoginResponse {
    private Long userId;
    private String userName;
    private String jwtToken;

    public LoginResponse(Long userId, String userName, String jwtToken) {
        this.userId = userId;
        this.userName = userName;
        this.jwtToken = jwtToken;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getJwtToken() {
        return jwtToken;
    }
}
