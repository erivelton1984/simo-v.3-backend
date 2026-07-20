package br.com.ciccr.simo.modules.role.controller;

import br.com.ciccr.simo.common.controller.BaseController;
import br.com.ciccr.simo.common.response.ApiResult;
import br.com.ciccr.simo.modules.role.dto.response.RoleResponse;
import br.com.ciccr.simo.modules.role.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController extends BaseController {

    private final RoleService service;

    @GetMapping
    public ResponseEntity<ApiResult<List<RoleResponse>>> findAll() {

        return ok(
                "Perfis encontrados.",
                service.findAll()
        );
    }

}