package com.esataydin.auth.dto;

public record AuthResponse(String token, String email, String role) {
}
