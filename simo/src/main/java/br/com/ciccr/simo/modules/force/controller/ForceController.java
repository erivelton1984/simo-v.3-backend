package br.com.ciccr.simo.modules.force.controller;

import br.com.ciccr.simo.common.controller.BaseController;
import br.com.ciccr.simo.common.response.ApiResponse;
import br.com.ciccr.simo.modules.force.constants.ForceMessages;
import br.com.ciccr.simo.modules.force.dto.request.CreateForceRequest;
import br.com.ciccr.simo.modules.force.dto.request.UpdateForceRequest;
import br.com.ciccr.simo.modules.force.dto.response.ForceResponse;
import br.com.ciccr.simo.modules.force.service.ForceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/forces")
@RequiredArgsConstructor
public class ForceController extends BaseController {

    private final ForceService service;

    @PostMapping
    public ResponseEntity<ApiResponse<ForceResponse>> create(@Valid @RequestBody CreateForceRequest request) {
        return created(ForceMessages.CREATED, service.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ForceResponse>> findById(@PathVariable Long id) {
        return ok(ForceMessages.FOUND, service.findById(id));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<ForceResponse>>> findAll(Pageable pageable) {
        return ok(ForceMessages.LIST, service.findAll(pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ForceResponse>> update(@PathVariable Long id,
                                                             @Valid @RequestBody UpdateForceRequest request) {
        return ok(ForceMessages.UPDATED, service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return noContent();
    }

}