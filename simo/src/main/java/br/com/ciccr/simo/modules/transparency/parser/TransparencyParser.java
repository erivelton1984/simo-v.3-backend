package br.com.ciccr.simo.modules.transparency.parser;

import br.com.ciccr.simo.common.exception.BusinessException;
import br.com.ciccr.simo.modules.transparency.dto.response.TransparencySearchResult;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class TransparencyParser {

    public TransparencySearchResult parse(String xml) {

        Document document = Jsoup.parse(
                xml,
                "",
                Parser.xmlParser()
        );

        String panel = extractPanel(document);

        String viewState = extractViewState(document);

        String commandId = extractCommandId(panel);

        return new TransparencySearchResult(
                panel,
                viewState,
                commandId
        );
    }

    private String extractPanel(Document document) {

        Element panel = document.selectFirst(
                "update[id=formRelacaoServidores:painelRelacaoServidores]"
        );

        if (panel == null) {
            throw new BusinessException(
                    "Resultado da pesquisa não encontrado."
            );
        }

        return panel.html();
    }

    private String extractViewState(Document document) {

        Element viewState = document.selectFirst(
                "update[id*=javax.faces.ViewState]"
        );

        if (viewState == null) {
            throw new BusinessException(
                    "ViewState não encontrado."
            );
        }

        return viewState.text();
    }

    private String extractCommandId(String panel) {

        Pattern pattern = Pattern.compile(
                "formRelacaoServidores:gridServidores:\\d+:j_idt\\d+"
        );

        Matcher matcher = pattern.matcher(panel);

        if (!matcher.find()) {
            throw new BusinessException(
                    "Servidor não encontrado."
            );
        }

        return matcher.group();
    }

}