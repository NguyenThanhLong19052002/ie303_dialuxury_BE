package com.ie303.dialuxury.model;

public class AuthResponse {
    private String token;
    private String userId;

    private String role;

    public AuthResponse(String token) {
        this.token = token;
    }

    public AuthResponse(String token, String userId, String role) {
        this.token = token;
        this.userId = userId;
        this.role = role;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
