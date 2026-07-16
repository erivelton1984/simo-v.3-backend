package br.com.ciccr.simo.auth.dto.response;

public record LoginResponse(

        String token,

        String type,

        Long expiresIn,

        AuthUserResponse user

) {
}