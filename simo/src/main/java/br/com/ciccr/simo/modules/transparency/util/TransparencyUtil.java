package br.com.ciccr.simo.modules.transparency.util;

import br.com.ciccr.simo.modules.force.enums.SecurityForce;
import br.com.ciccr.simo.modules.transparency.dto.response.TransparencyLinkResponse;

import java.util.List;
import java.util.Optional;

public final class TransparencyUtil {

    public static SecurityForce identifyForce(String quadro) {

        if (quadro == null) {
            return SecurityForce.OUTRO;
        }

        String codigo = quadro.toUpperCase();

        if (codigo.startsWith("PM")) {
            return SecurityForce.PMPR;
        }

        if (codigo.startsWith("BM")) {
            return SecurityForce.CBMPR;
        }

        if (codigo.startsWith("QPPC")) {
            return SecurityForce.PCPR;
        }

        if (codigo.startsWith("QPPO")) {
            return SecurityForce.POLICIA_CIENTIFICA;
        }

        if (codigo.startsWith("QPPP")) {
            return SecurityForce.POLICIA_PENAL;
        }

        return SecurityForce.OUTRO;
    }

    public static boolean isOperationalForce(String quadro) {
        return identifyForce(quadro) != SecurityForce.OUTRO;
    }

    public static Optional<TransparencyLinkResponse> findOperationalLink(
            List<TransparencyLinkResponse> links) {

        if (links == null || links.isEmpty()) {
            return Optional.empty();
        }

        return links.stream()
                .filter(link ->
                        isOperationalForce(link.functionalFramework())
                                && link.employmentStatus().isActive())
                .findFirst();
    }

    public static Optional<TransparencyLinkResponse> findForceLink(
            List<TransparencyLinkResponse> links) {

        if (links == null || links.isEmpty()) {
            return Optional.empty();
        }

        return links.stream()
                .filter(link -> isOperationalForce(link.functionalFramework()))
                .findFirst();
    }

    public static boolean isForceActive(
            List<TransparencyLinkResponse> links) {

        if (links == null || links.isEmpty()) {
            return false;
        }

        return links.stream()
                .anyMatch(link ->
                        isOperationalForce(link.functionalFramework())
                                && link.employmentStatus().isActive());
    }

    public static SecurityForce identifySecurityForce(
            List<TransparencyLinkResponse> links) {

        return findForceLink(links)
                .map(link -> identifyForce(link.functionalFramework()))
                .orElse(SecurityForce.OUTRO);
    }
}
