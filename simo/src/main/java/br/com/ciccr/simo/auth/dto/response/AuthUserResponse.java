package br.com.ciccr.simo.auth.dto.response;

public record AuthUserResponse(

        Long id,
        String fullName,
        String email,
        String role
) {
}