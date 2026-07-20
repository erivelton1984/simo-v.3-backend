package br.com.ciccr.simo.modules.transparency.controller;

import br.com.ciccr.simo.common.controller.BaseController;
import br.com.ciccr.simo.common.response.ApiResult;
import br.com.ciccr.simo.modules.transparency.dto.response.TransparencyResponse;
import br.com.ciccr.simo.modules.transparency.service.TransparencyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/v1/transparency")
@Tag(name = "Portal da Transparência")
public class TransparencyController extends BaseController {

    private final TransparencyService service;

    @GetMapping("/search")
    @Operation(summary = "Consulta um servidor no Portal da Transparência")
    public ResponseEntity<ApiResult<TransparencyResponse>> search(

            @RequestParam
            @NotBlank(message = "Informe o nome do servidor.")
            String name) {

        return ok(
                "Consulta realizada com sucesso.",
                service.search(name)
        );
    }

}