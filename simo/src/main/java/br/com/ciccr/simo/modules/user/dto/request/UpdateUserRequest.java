package br.com.ciccr.simo.modules.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateUserRequest(

        @NotBlank(message = "Nome é obrigatório.")
        @Size(max = 150)
        String fullName,

        @NotBlank(message = "E-mail é obrigatório.")
        @Email
        String email,

        @NotNull(message = "Perfil é obrigatório.")
        Long roleId,

        Boolean active

) {
}