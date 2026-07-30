// dto/AuthDTOs.java
package com.example.lembretes_Api.dto;

public class AuthDTOs {
    public record LoginRequest(String gmail, String password) {}
    public record TokenResponse(String token) {}
}
