package br.com.ciccr.simo.modules.transparency.parser;

import br.com.ciccr.simo.modules.transparency.dto.response.TransparencyLinkResponse;
import br.com.ciccr.simo.modules.transparency.dto.response.TransparencyResponse;
import br.com.ciccr.simo.modules.transparency.enums.EmploymentStatus;
import br.com.ciccr.simo.modules.transparency.enums.LinkType;
import br.com.ciccr.simo.modules.transparency.validator.TransparencyValidator;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TransparencyServerParser {

    private final TransparencyValidator validator;

    public TransparencyResponse parse(String html) {

        Document document = Jsoup.parse(html);

        String name = extractName(document);
        String cpf = extractCpf(document);

        List<TransparencyLinkResponse> links =
                extractLinks(document);

        return new TransparencyResponse(
                name,
                cpf,
                belongsToSecurityDepartment(links),
                links
        );
    }

    private String extractName(Document document) {

        Element element =
                document.selectFirst("#formExibirRemuneracao\\:nomeViajante");

        return element == null
                ? ""
                : element.text().trim();
    }

    private String extractCpf(Document document) {

        Element element =
                document.selectFirst("#formExibirRemuneracao\\:cpfViajante");

        return element == null
                ? ""
                : element.text().trim();
    }

    private List<TransparencyLinkResponse> extractLinks(Document document) {

        List<TransparencyLinkResponse> links = new ArrayList<>();

        Elements rows = document.select("table tbody tr");

        for (int i = 0; i < rows.size(); i++) {

            Element row = rows.get(i);
            Elements columns = row.select("td");

            if (columns.size() != 8) {
                continue;
            }

            String institution = columns.get(1).text().trim();
            String position = columns.get(3).text().trim();
            String functionalFramework = columns.get(6).text().trim();
            String employment = columns.get(7).text().trim();

            if (isSalaryRow(institution)) {
                continue;
            }

            institution = resolveInstitution(rows, i, institution);

            links.add(createLink(
                    institution,
                    position,
                    functionalFramework,
                    employment
            ));
        }

        return links;
    }

    private TransparencyLinkResponse createLink(
            String institution,
            String position,
            String functionalFramework,
            String employment) {

        return new TransparencyLinkResponse(
                institution,
                position,
                functionalFramework,
                resolveEmploymentStatus(employment),
                resolveLinkType(functionalFramework)
        );
    }

    private boolean belongsToSecurityDepartment(
            List<TransparencyLinkResponse> links) {

        return links.stream()
                .anyMatch(link ->
                        validator.belongsToSecurityDepartment(
                                link.institution()
                        ));
    }

    private boolean isSalaryRow(String institution) {

        return institution.matches(
                "(?i)(JAN|FEV|MAR|ABR|MAI|JUN|JUL|AGO|SET|OUT|NOV|DEZ)/\\d{4}"
        );
    }

    private String resolveInstitution(
            Elements rows,
            int index,
            String institution) {

        if (index + 1 >= rows.size()) {
            return institution;
        }

        String detail = rows.get(index + 1).text().trim();

        if (detail.contains("Local de Trabalho")) {

            String[] parts = detail.split("Local de Trabalho:");

            return parts[0].trim();
        }

        if (detail.contains("SECRETARIA")) {
            return detail;
        }

        return institution;
    }

    private EmploymentStatus resolveEmploymentStatus(String value) {
        return validator.resolveEmploymentStatus(value);
    }

    private LinkType resolveLinkType(String functionalFramework) {
        return validator.resolveLinkType(functionalFramework);
    }

}