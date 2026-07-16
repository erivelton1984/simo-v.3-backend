package br.com.ciccr.simo.auth.controller;

import br.com.ciccr.simo.auth.dto.request.LoginRequest;
import br.com.ciccr.simo.auth.dto.response.LoginResponse;
import br.com.ciccr.simo.auth.service.AuthenticationService;
import br.com.ciccr.simo.common.controller.BaseController;
import br.com.ciccr.simo.common.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController extends BaseController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        return ok("Login realizado com sucesso.", authenticationService.login(request));
    }
}