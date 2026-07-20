package br.com.ciccr.simo.modules.transparency.controller;


import br.com.ciccr.simo.modules.transparency.dto.response.ConsultaServidorResponse;

import br.com.ciccr.simo.modules.transparency.dto.response.SessionContext;
import br.com.ciccr.simo.modules.transparency.service.PortalService;
import br.com.ciccr.simo.modules.transparency.service.ServidorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/portal")
@Tag(name = "Portal da Transparência")
public class PortalController {

    private final PortalService portalService;
    private final ServidorService servidorService;

    public PortalController(
            PortalService portalService,
            ServidorService servidorService
    ) {
        this.portalService = portalService;
        this.servidorService = servidorService;
    }

    @GetMapping("/servidor")
    @Operation(summary = "Consulta um servidor no Portal da Transparência")
    public ConsultaServidorResponse consultar(
            @RequestParam String nome

    ) throws Exception {

        SessionContext session = portalService.abrirSessao();

        return servidorService.pesquisarServidor(session, nome);
    }
}