package br.com.ciccr.simo.modules.role.enums;

public enum RoleName {

    MASTER("Administrador do Sistema"),
    ADMIN("Administrador"),
    SUPERVISOR("Supervisor"),
    OPERADOR("Operador"),
    CONSULTA("Consulta");

    private final String description;

    RoleName(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}