package br.com.ciccr.simo.modules.transparency.parser;

import br.com.ciccr.simo.common.exception.BusinessException;
import br.com.ciccr.simo.modules.transparency.dto.response.TransparencySearchItem;
import br.com.ciccr.simo.modules.transparency.dto.response.TransparencySearchResult;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Entities;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TransparencyParser {

    public TransparencySearchResult parse(String xml) {

        Document document = parseXml(xml);

        String panel = extractPanel(document);

        return new TransparencySearchResult(
                extractViewState(document),
                extractItems(panel)
        );
    }

    private Document parseXml(String xml) {

        return Jsoup.parse(
                xml,
                "",
                Parser.xmlParser()
        );
    }

    private String extractPanel(Document document) {

        Element panel = getRequiredElement(
                document,
                "update[id=formRelacaoServidores:painelRelacaoServidores]",
                "Resultado da pesquisa não encontrado."
        );

        return Entities.unescape(panel.text());
    }

    private String extractViewState(Document document) {

        return getRequiredElement(
                document,
                "update[id*=javax.faces.ViewState]",
                "ViewState não encontrado."
        ).text();
    }

    private List<TransparencySearchItem> extractItems(String panel) {

        Document document = Jsoup.parse(panel);

        List<TransparencySearchItem> items = new ArrayList<>();

        Elements rows = document.select("tbody tr");

        for (Element row : rows) {

            Element action = row.selectFirst("a[id*=gridServidores]");

            if (action == null) {
                continue;
            }

            String commandId = action.id();

            Elements columns = row.select("td");

            if (columns.isEmpty()) {
                continue;
            }

            String name = columns.get(0).text().trim();

            String cpf = columns.size() > 1
                    ? columns.get(1).text().trim()
                    : "";

            if (name.isBlank()) {
                continue;
            }

            items.add(new TransparencySearchItem(
                    name,
                    cpf,
                    commandId
            ));
        }

        if (items.isEmpty()) {
            throw new BusinessException("Servidor não encontrado.");
        }

        return items;
    }

    private Element getRequiredElement(
            Document document,
            String selector,
            String errorMessage) {

        Element element = document.selectFirst(selector);

        if (element == null) {
            throw new BusinessException(errorMessage);
        }

        return element;
    }
}