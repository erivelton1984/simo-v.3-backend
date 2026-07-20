package br.com.ciccr.simo.modules.transparency.service;


import br.com.ciccr.simo.modules.transparency.client.PortalHttpClient;
import br.com.ciccr.simo.modules.transparency.dto.response.SessionContext;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;

@Service
public class PortalService {

    private final PortalHttpClient client;

    public PortalService(PortalHttpClient client) {
        this.client = client;
    }

    public SessionContext abrirSessao() throws Exception {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(
                        "https://www.transparencia.pr.gov.br/pte/pessoal/relacao-servidores?windowId=9f4"))
                .GET()
                .build();

        HttpResponse<String> response = client.getClient()
                .send(request, HttpResponse.BodyHandlers.ofString());

        Document document = Jsoup.parse(response.body());

        String viewState = document
                .selectFirst("input[name=javax.faces.ViewState]")
                .attr("value");

        if (viewState == null || viewState.isBlank()) {
            throw new IllegalStateException("ViewState não encontrado.");
        }

        String cookie = client.getCookieManager()
                .getCookieStore()
                .getCookies()
                .stream()
                .map(c -> c.getName() + "=" + c.getValue())
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("JSESSIONID não encontrado."));

        String query = response.uri().getQuery();

        String windowId = Arrays.stream(query.split("&"))
                .filter(p -> p.startsWith("windowId="))
                .map(p -> p.substring("windowId=".length()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("windowId não encontrado."));

        return new SessionContext(
                cookie,
                viewState,
                windowId
        );
    }
}