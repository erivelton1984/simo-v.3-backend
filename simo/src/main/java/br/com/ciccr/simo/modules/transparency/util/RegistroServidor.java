package br.com.ciccr.simo.modules.transparency.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistroServidor {

    private final String instituicao;
    private final String cargo;
    private final String quadroFuncional;
    private final String vinculo;

    public RegistroServidor(
            String instituicao,
            String cargo,
            String quadroFuncional,
            String vinculo) {

        this.instituicao = instituicao;
        this.cargo = cargo;
        this.quadroFuncional = quadroFuncional;
        this.vinculo = vinculo;
    }

    public boolean isAtivo() {
        return vinculo.equalsIgnoreCase("ATIVO");
    }

    public boolean isAposentado() {
        return vinculo.equalsIgnoreCase("APOSENTADO");
    }

    public boolean isPertenceSesp() {

        String texto = instituicao.toUpperCase();

        return texto.contains("SECRETARIA DA SEGURAN")
                || texto.contains("SESP");
    }

    public boolean isEstatutario() {

        String texto = quadroFuncional.toUpperCase();

        return texto.contains("POLÍCIA MILITAR")
                || texto.contains("POLICIA MILITAR")
                || texto.contains("POLÍCIA CIVIL")
                || texto.contains("POLICIA CIVIL")
                || texto.contains("POLÍCIA PENAL")
                || texto.contains("POLICIA PENAL")
                || texto.contains("BOMBEIRO")
                || texto.contains("PERITO")
                || texto.contains("PAPILOSCOPISTA");
    }

    public boolean isComissionado() {

        String texto = quadroFuncional.toUpperCase();

        return texto.contains("COMISSIONADO")
                || texto.contains("FUNÇÃO COMISSIONADA")
                || texto.contains("FUNCAO COMISSIONADA");
    }

    public String getInstituicao() {
        return instituicao;
    }

    public String getCargo() {
        return cargo;
    }

    public String getQuadroFuncional() {
        return quadroFuncional;
    }
}
