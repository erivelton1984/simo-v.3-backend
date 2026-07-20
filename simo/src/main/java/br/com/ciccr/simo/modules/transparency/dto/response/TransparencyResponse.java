package br.com.ciccr.simo.modules.transparency.dto.response;

import java.util.List;

public record TransparencyResponse(

        String nome,
        String cpf,
        boolean pertenceSesp,
        List<TransparencyLinkResponse> vinculos

) {
}