package br.com.ciccr.simo.modules.transparency.dto.response;


public record VinculoServidorResponse(

        String instituicao,
        String cargo,
        String quadroFuncional,
        String situacao,
        boolean pertenceSesp,
        boolean ativo,
        boolean comissionado,
        boolean estatutario

) {
}