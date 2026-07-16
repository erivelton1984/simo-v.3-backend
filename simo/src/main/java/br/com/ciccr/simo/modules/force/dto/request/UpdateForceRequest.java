package br.com.ciccr.simo.modules.force.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateForceRequest(

        @NotBlank(message = "O nome é obrigatório.")
        @Size(max = 100)
        String name,

        Boolean active

) {
}
