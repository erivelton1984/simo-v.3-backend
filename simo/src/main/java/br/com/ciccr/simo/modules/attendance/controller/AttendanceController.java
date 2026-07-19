package br.com.ciccr.simo.modules.attendance.controller;

import br.com.ciccr.simo.common.controller.BaseController;
import br.com.ciccr.simo.common.response.ApiResponse;
import br.com.ciccr.simo.modules.attendance.constants.AttendanceMessages;
import br.com.ciccr.simo.modules.attendance.dto.request.AttendanceFilterRequest;
import br.com.ciccr.simo.modules.attendance.dto.request.CreateAttendanceRequest;
import br.com.ciccr.simo.modules.attendance.dto.request.UpdateAttendanceRequest;
import br.com.ciccr.simo.modules.attendance.dto.response.AttendanceResponse;
import br.com.ciccr.simo.modules.attendance.dto.response.AttendanceSummaryResponse;
import br.com.ciccr.simo.modules.attendance.service.AttendanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/attendances")
@RequiredArgsConstructor
public class AttendanceController extends BaseController {

    private final AttendanceService service;

    @PostMapping
    public ResponseEntity<ApiResponse<AttendanceResponse>> create(@Valid @RequestBody CreateAttendanceRequest request) {
        return created(AttendanceMessages.CREATED, service.create(request)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AttendanceResponse>> findById(@PathVariable Long id) {
        return ok(AttendanceMessages.NOT_FOUND, service.findById(id));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<AttendanceSummaryResponse>>> findAll(
            @ModelAttribute AttendanceFilterRequest filter, Pageable pageable) {
        return ok(
                AttendanceMessages.LIST,
                service.findAll(filter, pageable)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AttendanceResponse>> update(@PathVariable Long id,
            @Valid @RequestBody UpdateAttendanceRequest request) {
        return ok(AttendanceMessages.UPDATED, service.update(id, request));
    }

    @PatchMapping("/{id}/close")
    public ResponseEntity<ApiResponse<Void>> close(@PathVariable Long id) {
        service.close(id);
        return ok(AttendanceMessages.CLOSED);
    }

    @PatchMapping("/{id}/reopen")
    public ResponseEntity<ApiResponse<Void>> reopen(@PathVariable Long id) {
        service.reopen(id);
        return ok(AttendanceMessages.REOPENED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return noContent();
    }
}