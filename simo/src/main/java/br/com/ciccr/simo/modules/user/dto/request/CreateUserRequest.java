package br.com.ciccr.simo.modules.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(

        @NotBlank(message = "Nome é obrigatório.")
        @Size(max = 150, message = "Nome deve possuir no máximo 150 caracteres.")
        String fullName,

        @NotBlank(message = "E-mail é obrigatório.")
        @Email(message = "E-mail inválido.")
        String email,

        @NotBlank(message = "Senha é obrigatória.")
        @Size(min = 8, max = 100,
                message = "A senha deve possuir entre 8 e 100 caracteres.")
        String password,

        @NotNull(message = "Perfil é obrigatório.")
        Long roleId

) {
}