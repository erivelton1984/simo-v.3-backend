package br.com.ciccr.simo.modules.attendance.service;

import br.com.ciccr.simo.modules.attendance.enums.AttendanceAction;
import br.com.ciccr.simo.modules.attendance.model.Attendance;

public interface AttendanceHistoryService {

    void registerHistory(
            Attendance attendance,
            AttendanceAction action,
            String description
    );

}
