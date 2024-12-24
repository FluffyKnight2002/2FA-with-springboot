package org.authentication.two_factor_auth.dto;

public record UserLoginDto(String username, String password, int otp) {
}
