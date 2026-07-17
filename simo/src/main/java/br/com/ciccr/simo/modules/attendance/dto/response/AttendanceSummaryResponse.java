package br.com.ciccr.simo.modules.attendance.dto.response;

import br.com.ciccr.simo.modules.attendance.enums.AttendanceStatus;
import java.time.LocalDateTime;

public record AttendanceSummaryResponse(

        Long id,

        String protocol,

        LocalDateTime attendanceDateTime,

        String forceName,

        String requester,

        String institution,

        String occurrenceNumber,

        AttendanceStatus status,

        Integer version,

        Boolean active,

        String createdUser

) {
}
