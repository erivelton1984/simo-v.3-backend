package br.com.ciccr.simo.modules.transparency.dto.response;

import br.com.ciccr.simo.modules.force.enums.SecurityForce;

import java.util.List;


public record TransparencyResponse(

        String nome,
        String cpf,
        boolean pertenceSesp,
        SecurityForce securityForce,
        boolean active,
        List<TransparencyLinkResponse> vinculos

) {
}