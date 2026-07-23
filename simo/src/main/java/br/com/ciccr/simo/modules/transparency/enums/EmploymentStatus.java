package br.com.ciccr.simo.modules.transparency.enums;

public enum EmploymentStatus {

    ATIVO,
    APOSENTADO,
    ENCERRADO,
    DESCONHECIDO;

    public boolean isActive() {
        return this == EmploymentStatus.ATIVO;
    }

}