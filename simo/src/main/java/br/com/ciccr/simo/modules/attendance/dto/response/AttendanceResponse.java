package br.com.ciccr.simo.modules.attendance.dto.response;

import br.com.ciccr.simo.modules.attendance.enums.AttendanceStatus;

import java.time.LocalDateTime;
import java.util.List;

public record AttendanceResponse(

        Long id,

        String protocol,

        LocalDateTime attendanceDateTime,

        Long forceId,

        String forceName,

        List<AttendanceTypeResponse> attendanceTypes,

        String server,

        String requester,

        String institution,

        String unit,

        String subUnit,

        String occurrenceNumber,

        Integer arrests,

        String observation,

        AttendanceStatus status,

        Integer version,

        Boolean active,

        String createdUser,

        String closedBy,

        LocalDateTime closedAt,

        LocalDateTime createdAt,

        LocalDateTime updatedAt

) {
}