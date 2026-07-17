package br.com.ciccr.simo.modules.attendance.dto.response;

import br.com.ciccr.simo.modules.attendance.enums.AttendanceAction;

import java.time.LocalDateTime;

public record AttendanceHistoryResponse(

        Long id,

        Long attendanceId,

        Integer version,

        AttendanceAction action,

        String description,

        String createdBy,

        LocalDateTime createdAt

) {
}