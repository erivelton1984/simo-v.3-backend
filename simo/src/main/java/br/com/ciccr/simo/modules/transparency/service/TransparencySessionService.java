package br.com.ciccr.simo.modules.transparency.service;

import br.com.ciccr.simo.modules.transparency.client.TransparencyHttpClient;
import br.com.ciccr.simo.modules.transparency.dto.response.TransparencySessionResponse;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class TransparencySessionService {

    private static final String PORTAL_URL =
            "https://www.transparencia.pr.gov.br/pte/pessoal/relacao-servidores?windowId=9f4";

    private final TransparencyHttpClient httpClient;

    public TransparencySessionResponse openSession() throws Exception {

        HttpResponse<String> response = executeRequest();

        String viewState = extractViewState(response.body());

        String cookie = extractSessionCookie();

        String windowId = extractWindowId(response);

        return new TransparencySessionResponse(
                cookie,
                viewState,
                windowId
        );
    }

    private HttpResponse<String> executeRequest() throws Exception {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(PORTAL_URL))
                .GET()
                .build();

        return httpClient
                .getClient()
                .send(request, HttpResponse.BodyHandlers.ofString());
    }

    private String extractViewState(String html) {

        Document document = Jsoup.parse(html);

        String viewState = document
                .selectFirst("input[name=javax.faces.ViewState]")
                .attr("value");

        if (viewState == null || viewState.isBlank()) {
            throw new IllegalStateException("ViewState não encontrado.");
        }

        return viewState;
    }

    private String extractSessionCookie() {

        return httpClient.getCookieManager()
                .getCookieStore()
                .getCookies()
                .stream()
                .map(cookie -> cookie.getName() + "=" + cookie.getValue())
                .findFirst()
                .orElseThrow(() ->
                        new IllegalStateException("JSESSIONID não encontrado."));
    }

    private String extractWindowId(HttpResponse<String> response) {

        String query = response.uri().getQuery();

        return Arrays.stream(query.split("&"))
                .filter(parameter -> parameter.startsWith("windowId="))
                .map(parameter -> parameter.substring("windowId=".length()))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalStateException("windowId não encontrado."));
    }

}