package br.com.ciccr.simo.modules.attendance.repository;

import br.com.ciccr.simo.common.repository.BaseRepository;
import br.com.ciccr.simo.modules.attendance.model.AttendanceType;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AttendanceTypeRepository
        extends BaseRepository<AttendanceType, Long> {

    boolean existsByNameIgnoreCase(String name);

    Optional<AttendanceType> findByNameIgnoreCase(String name);

}