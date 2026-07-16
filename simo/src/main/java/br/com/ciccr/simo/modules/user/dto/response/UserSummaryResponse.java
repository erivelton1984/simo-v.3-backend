package br.com.ciccr.simo.modules.user.dto.response;

public record UserSummaryResponse(

        Long id,

        String fullName,

        String email,

        String role,

        Boolean active

) {
}