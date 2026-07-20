package br.com.ciccr.simo.modules.transparency.service;


import br.com.ciccr.simo.modules.transparency.client.PortalHttpClient;
import br.com.ciccr.simo.modules.transparency.dto.response.ConsultaServidorResponse;
import br.com.ciccr.simo.modules.transparency.dto.response.SessionContext;
import br.com.ciccr.simo.modules.transparency.dto.response.VinculoServidorResponse;
import br.com.ciccr.simo.modules.transparency.util.RegistroServidor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ServidorService {

    private static final String URL =
            "https://www.transparencia.pr.gov.br/pte/pessoal/relacao-servidores";

    private static final String INSTITUICAO =
            "SESP - SECRETARIA DA SEGURANCA PUBLICA E ADMINISTRACAO PENITENCIARIA";

    private final PortalHttpClient client;

    public ServidorService(PortalHttpClient client) {
        this.client = client;
    }

    public ConsultaServidorResponse pesquisarServidor(SessionContext session, String nome) throws Exception {

        HttpResponse<String> response = executarPesquisa(session, nome);

        Document xml = Jsoup.parse(
                response.body(),
                "",
                Parser.xmlParser()
        );

        String html = extrairPainel(xml);

        String novoViewState = extrairViewState(xml);

        String commandId = extrairCommandId(html);

        String detalhes = abrirDetalhes(
                session,
                nome,
                novoViewState,
                commandId
        );

        return extrairDados(detalhes);
    }

    private HttpResponse<String> executarPesquisa(SessionContext session, String nome) throws Exception {

        String body =
                "windowId=" + session.windowId() +
                        "&javax.faces.partial.ajax=true" +
                        "&javax.faces.source=formRelacaoServidores:btnPesquisar" +
                        "&javax.faces.partial.execute=formRelacaoServidores" +
                        "&javax.faces.partial.render=formRelacaoServidores:painelRelacaoServidores" +
                        "&formRelacaoServidores=formRelacaoServidores" +
                        "&formRelacaoServidores:btnPesquisar=formRelacaoServidores:btnPesquisar" +
                        "&javax.faces.ViewState=" +
                        URLEncoder.encode(session.viewState(), StandardCharsets.UTF_8) +
                        "&formRelacaoServidores:combo-instituicao_input=" +
                        URLEncoder.encode(INSTITUICAO, StandardCharsets.UTF_8) +
                        "&formRelacaoServidores:filtroNome=" +
                        URLEncoder.encode(nome, StandardCharsets.UTF_8) +
                        "&formRelacaoServidores:combo-quadro-funcional_input=" +
                        "&formRelacaoServidores:j_idt99_input=on";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL + "?windowId=" + session.windowId()))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Faces-Request", "partial/ajax")
                .header("X-Requested-With", "XMLHttpRequest")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        return client.getClient().send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );
    }

    private String abrirDetalhes(SessionContext session, String nome, String viewState, String commandId) throws Exception {

        String body =
                "formRelacaoServidores=formRelacaoServidores" +

                        "&javax.faces.ViewState=" +
                        URLEncoder.encode(viewState, StandardCharsets.UTF_8) +

                        "&formRelacaoServidores:combo-instituicao_focus=SES" +

                        "&formRelacaoServidores:combo-instituicao_input=" +
                        URLEncoder.encode(INSTITUICAO, StandardCharsets.UTF_8) +

                        "&formRelacaoServidores:filtroNome=" +
                        URLEncoder.encode(nome, StandardCharsets.UTF_8) +

                        "&formRelacaoServidores:combo-quadro-funcional_focus=" +

                        "&formRelacaoServidores:combo-quadro-funcional_input=" +

                        "&formRelacaoServidores:j_idt99_input=on" +

                        "&" + commandId + "=" + commandId;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL + "?windowId=" + session.windowId()))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Cookie", session.cookie())
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        HttpResponse<String> response =
                client.getClient().send(
                        request,
                        HttpResponse.BodyHandlers.ofString()
                );

        return response.body();
    }

    private String extrairPainel(Document xml) {

        Element painel = xml.selectFirst(
                "update[id=formRelacaoServidores:painelRelacaoServidores]"
        );

        return painel == null ? "" : painel.html();
    }

    private String extrairViewState(Document xml) {

        Element viewState = xml.selectFirst(
                "update[id*=javax.faces.ViewState]"
        );

        return viewState == null ? "" : viewState.text();
    }

    private String extrairCommandId(String html) {

        Pattern pattern = Pattern.compile(
                "formRelacaoServidores:gridServidores:\\d+:j_idt\\d+"
        );

        Matcher matcher = pattern.matcher(html);

        if (!matcher.find()) {
            throw new IllegalStateException(
                    "Servidor não encontrado na pesquisa."
            );
        }

        return matcher.group();
    }

    private ConsultaServidorResponse extrairDados(String html) {

        Document doc = Jsoup.parse(html);

        String nome = extrairNome(doc);

        String cpf = extrairCpf(doc);

        List<VinculoServidorResponse> vinculos =
                extrairRegistros(doc);

        boolean pertenceSesp =
                vinculos.stream()
                        .anyMatch(VinculoServidorResponse::pertenceSesp);

        return new ConsultaServidorResponse(
                nome,
                cpf,
                pertenceSesp,
                vinculos
        );
    }

    private String extrairNome(Document doc) {

        Element element =
                doc.selectFirst("#formExibirRemuneracao\\:nomeViajante");

        return element != null
                ? element.text().trim()
                : "";
    }

    private String extrairCpf(Document doc) {

        Element element =
                doc.selectFirst("#formExibirRemuneracao\\:cpfViajante");

        return element != null
                ? element.text().trim()
                : "";
    }

    private List<VinculoServidorResponse> extrairRegistros(Document doc) {

        List<VinculoServidorResponse> vinculos = new ArrayList<>();

        Elements linhas = doc.select("table tbody tr");

        for (int i = 0; i < linhas.size(); i++) {

            Element linha = linhas.get(i);

            Elements colunas = linha.select("td");

            // Somente linhas da tabela funcional possuem 8 colunas
            if (colunas.size() != 8) {
                continue;
            }

            String instituicao = colunas.get(1).text().trim();
            String cargo = colunas.get(3).text().trim();
            String quadroFuncional = colunas.get(6).text().trim();
            String situacao = colunas.get(7).text().trim();

            // Ignora linhas da tabela de remuneração
            if (instituicao.matches("(?i)(JAN|FEV|MAR|ABR|MAI|JUN|JUL|AGO|SET|OUT|NOV|DEZ)/\\d{4}")) {
                continue;
            }

            String localTrabalho = "";

            // A linha seguinte normalmente contém os complementos
            if (i + 1 < linhas.size()) {

                String complemento = linhas.get(i + 1).text().trim();

                if (complemento.contains("Local de Trabalho")) {

                    String[] partes =
                            complemento.split("Local de Trabalho:");

                    if (partes.length > 0) {
                        instituicao = partes[0].trim();
                    }

                    if (partes.length > 1) {
                        localTrabalho = partes[1].trim();
                    }

                } else if (complemento.contains("SECRETARIA")) {

                    instituicao = complemento;
                }
            }

            RegistroServidor registro = new RegistroServidor(
                    instituicao,
                    cargo,
                    quadroFuncional,
                    situacao
            );

            vinculos.add(new VinculoServidorResponse(
                    registro.getInstituicao(),
                    registro.getCargo(),
                    registro.getQuadroFuncional(),
                    registro.getVinculo(),
                    registro.isPertenceSesp(),
                    registro.isAtivo(),
                    registro.isComissionado(),
                    registro.isEstatutario()
            ));
        }

        return vinculos;
    }
}