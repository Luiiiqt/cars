package com.lui.cars.dto;

public record AuthResponse(String token, String username, Long expiresAt) {
}
