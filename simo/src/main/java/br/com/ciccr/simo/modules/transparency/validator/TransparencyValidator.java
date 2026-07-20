package br.com.ciccr.simo.modules.transparency.validator;

import br.com.ciccr.simo.modules.transparency.enums.EmploymentStatus;
import br.com.ciccr.simo.modules.transparency.enums.LinkType;
import org.springframework.stereotype.Component;

@Component
public class TransparencyValidator {

    public boolean belongsToSecurityDepartment(String institution) {

        if (institution == null || institution.isBlank()) {
            return false;
        }

        String text = institution.toUpperCase();

        return text.contains("SECRETARIA DA SEGURAN")
                || text.contains("SESP");
    }

    public EmploymentStatus resolveEmploymentStatus(String value) {

        if (value == null || value.isBlank()) {
            return EmploymentStatus.DESCONHECIDO;
        }

        String text = value.toUpperCase();

        if (text.contains("ATIVO")) {
            return EmploymentStatus.ATIVO;
        }

        if (text.contains("APOSENTADO")) {
            return EmploymentStatus.APOSENTADO;
        }

        if (text.contains("ENCERRADO")) {
            return EmploymentStatus.ENCERRADO;
        }

        return EmploymentStatus.DESCONHECIDO;
    }

    public LinkType resolveLinkType(String functionalFramework) {

        if (functionalFramework == null || functionalFramework.isBlank()) {
            return LinkType.OUTRO;
        }

        String text = functionalFramework.toUpperCase();

        if (text.contains("POLÍCIA MILITAR")
                || text.contains("POLICIA MILITAR")) {
            return LinkType.PMPR;
        }

        if (text.contains("POLÍCIA CIVIL")
                || text.contains("POLICIA CIVIL")) {
            return LinkType.PCPR;
        }

        if (text.contains("BOMBEIRO")) {
            return LinkType.CBMPR;
        }

        if (text.contains("POLÍCIA PENAL")
                || text.contains("POLICIA PENAL")) {
            return LinkType.POLICIA_PENAL;
        }

        if (text.contains("PERITO")
                || text.contains("PAPILOSCOPISTA")
                || text.contains("POLÍCIA CIENTÍFICA")
                || text.contains("POLICIA CIENTIFICA")) {
            return LinkType.POLICIA_CIENTIFICA;
        }

        if (text.contains("COMISSIONADO")
                || text.contains("FUNÇÃO COMISSIONADA")
                || text.contains("FUNCAO COMISSIONADA")) {
            return LinkType.CARGO_COMISSIONADO;
        }

        if (text.contains("SERVIDOR")
                || text.contains("AGENTE PROFISSIONAL")
                || text.contains("AGENTE DE EXECUÇÃO")
                || text.contains("AGENTE DE APOIO")) {
            return LinkType.SERVIDOR_CIVIL;
        }

        return LinkType.OUTRO;
    }

}