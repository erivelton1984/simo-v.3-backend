package br.com.ciccr.simo.modules.transparency.service;

import br.com.ciccr.simo.common.exception.BusinessException;
import br.com.ciccr.simo.modules.transparency.client.TransparencyHttpClient;
import br.com.ciccr.simo.modules.transparency.dto.response.TransparencyResponse;
import br.com.ciccr.simo.modules.transparency.dto.response.TransparencySessionResponse;
import br.com.ciccr.simo.modules.transparency.parser.TransparencyServerParser;
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

    private final TransparencyHttpClient httpClient;
    private final TransparencyServerParser parser;

    public TransparencyResponse searchServer(
            TransparencySessionResponse session,
            String name) {

        try {

            HttpResponse<String> response =
                    httpClient.getClient().send(
                            buildRequest(session, name),
                            HttpResponse.BodyHandlers.ofString()
                    );

            return parser.parse(response.body());

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

    private HttpRequest buildRequest(
            TransparencySessionResponse session,
            String name) {

        return HttpRequest.newBuilder()
                .uri(URI.create(
                        BASE_URL + "?windowId=" + session.windowId()
                ))
                .header(
                        "Content-Type",
                        "application/x-www-form-urlencoded"
                )
                .header(
                        "Cookie",
                        session.cookie()
                )
                .POST(HttpRequest.BodyPublishers.ofString(
                        buildForm(session, name)
                ))
                .build();
    }

    private String buildForm(
            TransparencySessionResponse session,
            String name) {

        return "javax.faces.partial.ajax=true"
                + "&javax.faces.source=form:btnPesquisar"
                + "&javax.faces.partial.execute=form"
                + "&javax.faces.partial.render=form:resultado"
                + "&form=form"
                + "&form:nome=" + encode(name)
                + "&javax.faces.ViewState=" + encode(session.viewState());
    }

    private String encode(String value) {

        return URLEncoder.encode(
                value,
                StandardCharsets.UTF_8
        );
    }

}