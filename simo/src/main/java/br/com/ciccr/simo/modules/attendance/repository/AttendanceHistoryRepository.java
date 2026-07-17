package br.com.ciccr.simo.modules.attendance.repository;

import br.com.ciccr.simo.common.repository.BaseRepository;
import br.com.ciccr.simo.modules.attendance.model.AttendanceHistory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttendanceHistoryRepository
        extends BaseRepository<AttendanceHistory, Long> {

    List<AttendanceHistory> findByAttendanceIdOrderByVersionDesc(Long attendanceId);

}