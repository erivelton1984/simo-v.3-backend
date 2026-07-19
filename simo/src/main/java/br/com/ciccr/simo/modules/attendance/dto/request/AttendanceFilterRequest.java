package br.com.ciccr.simo.modules.attendance.dto.request;

import br.com.ciccr.simo.modules.attendance.enums.AttendanceStatus;

import java.time.LocalDate;

public record AttendanceFilterRequest(

        Long forceId,

        AttendanceStatus status,

        String protocol,

        LocalDate initialDate,

        LocalDate finalDate,

        Boolean active

) {
}