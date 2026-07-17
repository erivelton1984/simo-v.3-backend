package br.com.ciccr.simo.modules.attendance.repository;

import br.com.ciccr.simo.common.repository.BaseRepository;
import br.com.ciccr.simo.modules.attendance.model.Attendance;
import br.com.ciccr.simo.modules.attendance.enums.AttendanceStatus;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AttendanceRepository
        extends BaseRepository<Attendance, Long> {

    Optional<Attendance> findByProtocol(String protocol);

    boolean existsByProtocol(String protocol);

    long countByStatus(AttendanceStatus status);

}