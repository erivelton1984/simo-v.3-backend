package br.com.ciccr.simo.modules.attendance.controller;

import br.com.ciccr.simo.common.controller.BaseController;
import br.com.ciccr.simo.common.response.ApiResult;
import br.com.ciccr.simo.modules.attendance.constants.AttendanceMessages;
import br.com.ciccr.simo.modules.attendance.dto.request.AttendanceFilterRequest;
import br.com.ciccr.simo.modules.attendance.dto.request.CreateAttendanceRequest;
import br.com.ciccr.simo.modules.attendance.dto.request.UpdateAttendanceRequest;
import br.com.ciccr.simo.modules.attendance.dto.response.AttendanceResponse;
import br.com.ciccr.simo.modules.attendance.dto.response.AttendanceSummaryResponse;
import br.com.ciccr.simo.modules.attendance.service.AttendanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Atendimentos",
    description = "Gerenciamento dos atendimentos do SIMO."
)
@RestController
@RequestMapping("/attendances")
@RequiredArgsConstructor
public class AttendanceController extends BaseController {

    private final AttendanceService service;

    @Operation(summary = "Cadastrar atendimento", description = "Realiza o cadastro de um novo atendimento.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Atendimento criado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos."),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado."),
            @ApiResponse(responseCode = "403", description = "Acesso negado.")
    })
    @PostMapping
    public ResponseEntity<ApiResult<AttendanceResponse>> create(@Valid @RequestBody CreateAttendanceRequest request) {
        return created(AttendanceMessages.CREATED, service.create(request)
        );
    }

    @Operation(summary = "Cadastrar atendimento", description = "Realiza a busca de um atendimento em específico.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Atendimento criado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos."),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado."),
            @ApiResponse(responseCode = "403", description = "Acesso negado.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResult<AttendanceResponse>> findById(@PathVariable Long id) {
        return ok(AttendanceMessages.NOT_FOUND, service.findById(id));
    }

    @Operation(summary = "Cadastrar atendimento", description = "Realiza a busca de todos os atendimentos.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Atendimento criado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos."),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado."),
            @ApiResponse(responseCode = "403", description = "Acesso negado.")
    })
    @GetMapping
    public ResponseEntity<ApiResult<Page<AttendanceSummaryResponse>>> findAll(
            @ModelAttribute AttendanceFilterRequest filter, Pageable pageable) {
        return ok(
                AttendanceMessages.LIST,
                service.findAll(filter, pageable)
        );
    }

    @Operation(summary = "Cadastrar atendimento", description = "Realiza a atualização do atendimento.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Atendimento criado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos."),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado."),
            @ApiResponse(responseCode = "403", description = "Acesso negado.")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ApiResult<AttendanceResponse>> update(@PathVariable Long id,
                                                                @Valid @RequestBody UpdateAttendanceRequest request) {
        return ok(AttendanceMessages.UPDATED, service.update(id, request));
    }

    @Operation(summary = "Cadastrar atendimento", description = "Realiza o fechamento do atendimento.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Atendimento criado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos."),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado."),
            @ApiResponse(responseCode = "403", description = "Acesso negado.")
    })
    @PatchMapping("/{id}/close")
    public ResponseEntity<ApiResult<Void>> close(@PathVariable Long id) {
        service.close(id);
        return ok(AttendanceMessages.CLOSED);
    }

    @Operation(summary = "Cadastrar atendimento", description = "Realiza a reabertura do atendimento.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Atendimento criado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos."),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado."),
            @ApiResponse(responseCode = "403", description = "Acesso negado.")
    })
    @PatchMapping("/{id}/reopen")
    public ResponseEntity<ApiResult<Void>> reopen(@PathVariable Long id) {
        service.reopen(id);
        return ok(AttendanceMessages.REOPENED);
    }

    @Operation(summary = "Cadastrar atendimento", description = "Realiza a exclusão do atendimento.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Atendimento criado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos."),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado."),
            @ApiResponse(responseCode = "403", description = "Acesso negado.")
    })
    @PostMapping
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return noContent();
    }
}