package br.com.ciccr.simo.modules.attendance.service;

import br.com.ciccr.simo.modules.attendance.dto.request.AttendanceFilterRequest;
import br.com.ciccr.simo.modules.attendance.dto.request.CreateAttendanceRequest;
import br.com.ciccr.simo.modules.attendance.dto.request.UpdateAttendanceRequest;
import br.com.ciccr.simo.modules.attendance.dto.response.AttendanceResponse;
import br.com.ciccr.simo.modules.attendance.dto.response.AttendanceSummaryResponse;
import br.com.ciccr.simo.modules.attendance.enums.AttendanceStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface AttendanceService {

    AttendanceResponse create(CreateAttendanceRequest request);

    AttendanceResponse update(Long id, UpdateAttendanceRequest request);

    AttendanceResponse findById(Long id);

    Page<AttendanceSummaryResponse> findAll(AttendanceFilterRequest filter,Pageable pageable);

    void close(Long id);

    void reopen(Long id);

    void delete(Long id);

}