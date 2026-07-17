package br.com.ciccr.simo.modules.attendance.snapshot;

import br.com.ciccr.simo.modules.attendance.enums.AttendanceStatus;

import java.time.LocalDateTime;
import java.util.List;

public record AttendanceSnapshot(

        String protocol,
        LocalDateTime attendanceDateTime,
        String force,
        List<String> attendanceTypes,
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
        LocalDateTime closedAt
) {
}