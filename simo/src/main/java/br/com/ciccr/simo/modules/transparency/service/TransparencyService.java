package br.com.ciccr.simo.modules.transparency.service;

import br.com.ciccr.simo.common.exception.BusinessException;
import br.com.ciccr.simo.modules.transparency.client.TransparencyHttpClient;
import br.com.ciccr.simo.modules.transparency.dto.response.*;
import br.com.ciccr.simo.modules.transparency.parser.TransparencyParser;
import br.com.ciccr.simo.modules.transparency.parser.TransparencyServerParser;
import br.com.ciccr.simo.modules.transparency.util.TransparencySearchMatcher;
import br.com.ciccr.simo.modules.transparency.util.TransparencyUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class TransparencyService {

    private static final String BASE_URL =
            "https://www.transparencia.pr.gov.br/pte/pessoal/relacao-servidores";

    private static final String INSTITUTION =
            "SESP - SECRETARIA DA SEGURANCA PUBLICA E ADMINISTRACAO PENITENCIARIA";

    private final TransparencyHttpClient httpClient;
    private final TransparencyParser transparencyParser;
    private final TransparencyServerParser serverParser;
    private final TransparencySessionService sessionService;

    public TransparencyResponse search(String name) {

        try {

            TransparencySessionResponse session =
                    sessionService.openSession();

            TransparencySearchResult searchResult =
                    searchServer(session, name);

            TransparencySearchItem selected =
                    findBestMatch(name, searchResult);

            String details =
                    openDetails(session, name, searchResult.viewState(), selected.commandId());

            return enrichResponse(
                    serverParser.parse(details)
            );

        } catch (IOException ex) {

            throw new BusinessException(
                    "Erro ao consultar o Portal da Transparência."
            );

        } catch (InterruptedException ex) {

            Thread.currentThread().interrupt();

            throw new BusinessException(
                    "Consulta ao Portal da Transparência interrompida."
            );
        }
    }

    private TransparencySearchResult searchServer(
            TransparencySessionResponse session,
            String name)
            throws IOException, InterruptedException {

        HttpResponse<String> response =
                httpClient.getClient().send(
                        buildSearchRequest(session, name),
                        HttpResponse.BodyHandlers.ofString()
                );

        return transparencyParser.parse(response.body());
    }

    private String openDetails(
            TransparencySessionResponse session,
            String name,
            String viewState,
            String commandId)
            throws IOException, InterruptedException {

        HttpResponse<String> response =
                httpClient.getClient().send(
                        buildDetailsRequest(session, name, viewState, commandId),
                        HttpResponse.BodyHandlers.ofString()
                );

        return response.body();
    }

    private TransparencySearchItem findBestMatch(
            String searchedName,
            TransparencySearchResult result) {

        TransparencySearchItem item =
                TransparencySearchMatcher.findBestMatch(
                        searchedName,
                        result.items()
                );

        if (item == null) {
            throw new BusinessException("Servidor não encontrado.");
        }

        return item;
    }

    private HttpRequest buildSearchRequest(
            TransparencySessionResponse session,
            String name) {

        return HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "?windowId=" + session.windowId()))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Faces-Request", "partial/ajax")
                .header("X-Requested-With", "XMLHttpRequest")
                .header("Cookie", session.cookie())
                .POST(HttpRequest.BodyPublishers.ofString(
                        buildSearchForm(session, name)
                ))
                .build();
    }

    private HttpRequest buildDetailsRequest(
            TransparencySessionResponse session,
            String name,
            String viewState,
            String commandId) {

        return HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "?windowId=" + session.windowId()))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Cookie", session.cookie())
                .POST(HttpRequest.BodyPublishers.ofString(
                        buildDetailsForm(name, viewState, commandId)
                ))
                .build();
    }

    private String buildSearchForm(
            TransparencySessionResponse session,
            String name) {

        return "windowId=" + session.windowId()
                + "&javax.faces.partial.ajax=true"
                + "&javax.faces.source=formRelacaoServidores:btnPesquisar"
                + "&javax.faces.partial.execute=formRelacaoServidores"
                + "&javax.faces.partial.render=formRelacaoServidores:painelRelacaoServidores"
                + "&formRelacaoServidores=formRelacaoServidores"
                + "&formRelacaoServidores:btnPesquisar=formRelacaoServidores:btnPesquisar"
                + "&javax.faces.ViewState=" + encode(session.viewState())
                + "&formRelacaoServidores:combo-instituicao_input=" + encode(INSTITUTION)
                + "&formRelacaoServidores:filtroNome=" + encode(name)
                + "&formRelacaoServidores:combo-quadro-funcional_input="
                + "&formRelacaoServidores:j_idt99_input=on";
    }

    private String buildDetailsForm(
            String name,
            String viewState,
            String commandId) {

        return "formRelacaoServidores=formRelacaoServidores"
                + "&javax.faces.ViewState=" + encode(viewState)
                + "&formRelacaoServidores:combo-instituicao_focus=SES"
                + "&formRelacaoServidores:combo-instituicao_input=" + encode(INSTITUTION)
                + "&formRelacaoServidores:filtroNome=" + encode(name)
                + "&formRelacaoServidores:combo-quadro-funcional_focus="
                + "&formRelacaoServidores:combo-quadro-funcional_input="
                + "&formRelacaoServidores:j_idt99_input=on"
                + "&" + commandId + "=" + commandId;
    }

    private String encode(String value) {

        return URLEncoder.encode(
                value,
                StandardCharsets.UTF_8
        );
    }

    private TransparencyResponse enrichResponse(
            TransparencyResponse response) {

        return new TransparencyResponse(
                response.nome(),
                response.cpf(),
                response.pertenceSesp(),
                TransparencyUtil.identifySecurityForce(response.vinculos()),
                TransparencyUtil.isForceActive(response.vinculos()),
                response.vinculos()
        );
    }
}