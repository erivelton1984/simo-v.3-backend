package br.com.ciccr.simo.modules.transparency.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.net.http.HttpResponse;
import java.util.Arrays;

@Component
public class TransparencyParser {

    public String extractViewState(String html) {

        Document document = Jsoup.parse(html);

        var element = document.selectFirst(
                "input[name=javax.faces.ViewState]"
        );

        if (element == null) {
            throw new IllegalStateException(
                    "ViewState não encontrado."
            );
        }

        String viewState = element.attr("value");

        if (viewState.isBlank()) {
            throw new IllegalStateException(
                    "ViewState inválido."
            );
        }

        return viewState;
    }

    public String extractWindowId(HttpResponse<String> response) {

        String query = response.uri().getQuery();

        if (query == null || query.isBlank()) {
            throw new IllegalStateException(
                    "windowId não encontrado."
            );
        }

        return Arrays.stream(query.split("&"))
                .filter(parameter -> parameter.startsWith("windowId="))
                .map(parameter -> parameter.substring("windowId=".length()))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalStateException(
                                "windowId não encontrado."
                        ));
    }

}