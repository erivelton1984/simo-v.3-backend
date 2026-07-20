package br.com.ciccr.simo.modules.transparency.validator;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class TransparencyValidator {

    public boolean belongsToPublicSecurity(
            TransparencyServerResponse response) {

        if (response.getInstitution() == null) {
            return false;
        }

        String text =
                response.getInstitution().toUpperCase();

        return text.contains("SECRETARIA DA SEGURAN")
                || text.contains("SESP");
    }

    public boolean isActive(
            TransparencyServerResponse response) {

        return response.getEmploymentStatus() != null
                && response.getEmploymentStatus().isActive();
    }

    public boolean isEffectiveEmployee(
            TransparencyServerResponse response) {

        if (response.getFunctionalFramework() == null) {
            return false;
        }

        String text =
                response.getFunctionalFramework().toUpperCase();

        return text.contains("POLÍCIA MILITAR")
                || text.contains("POLICIA MILITAR")
                || text.contains("POLÍCIA CIVIL")
                || text.contains("POLICIA CIVIL")
                || text.contains("POLÍCIA PENAL")
                || text.contains("POLICIA PENAL")
                || text.contains("BOMBEIRO")
                || text.contains("PERITO")
                || text.contains("PAPILOSCOPISTA");
    }

    public boolean isCommissioned(
            TransparencyServerResponse response) {

        if (response.getFunctionalFramework() == null) {
            return false;
        }

        String text =
                response.getFunctionalFramework().toUpperCase();

        return text.contains("COMISSIONADO")
                || text.contains("FUNÇÃO COMISSIONADA")
                || text.contains("FUNCAO COMISSIONADA");
    }

}