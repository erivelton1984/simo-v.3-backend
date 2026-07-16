package br.com.ciccr.simo.modules.user.dto.response;

import java.time.LocalDateTime;

public record UserResponse(

        Long id,

        String fullName,

        String email,

        String role,

        Boolean active,

        LocalDateTime createdAt,

        LocalDateTime updatedAt,

        String createdBy,

        String updatedBy

) {
}