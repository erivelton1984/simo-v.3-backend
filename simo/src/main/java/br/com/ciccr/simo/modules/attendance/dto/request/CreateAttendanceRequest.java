package br.com.ciccr.simo.modules.attendance.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public record CreateAttendanceRequest(

        @NotNull
        LocalDateTime attendanceDateTime,

        @NotNull
        Long forceId,

        @NotEmpty
        List<Long> attendanceTypeIds,

        String server,

        String requester,

        String institution,

        String unit,

        String subUnit,

        String occurrenceNumber,

        Integer arrests,

        String observation

) {
}