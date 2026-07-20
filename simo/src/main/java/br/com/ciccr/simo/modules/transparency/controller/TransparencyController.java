package br.com.ciccr.simo.modules.transparency.controller;

import br.com.ciccr.simo.modules.transparency.dto.response.TransparencyResponse;
import br.com.ciccr.simo.modules.transparency.dto.response.TransparencySessionResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/portal")
public class TransparencyController {

    private final PortalService portalService;
    private final ServidorService servidorService;

    public TransparencyController(
            PortalService portalService,
            ServidorService servidorService
    ) {
        this.portalService = portalService;
        this.servidorService = servidorService;
    }

    @GetMapping("/servidor")
    public TransparencyResponse consultar(
            @RequestParam String nome

    ) throws Exception {

        TransparencySessionResponse session = portalService.abrirSessao();

        return servidorService.pesquisarServidor(
                session,
                nome
        );
    }
}