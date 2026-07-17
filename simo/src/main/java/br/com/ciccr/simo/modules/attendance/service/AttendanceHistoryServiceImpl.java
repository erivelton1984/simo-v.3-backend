package br.com.ciccr.simo.modules.attendance.service;

import br.com.ciccr.simo.auth.current.CurrentUserService;
import br.com.ciccr.simo.common.service.BaseService;
import br.com.ciccr.simo.common.util.ProtocolGenerator;
import br.com.ciccr.simo.modules.attendance.dto.request.CreateAttendanceRequest;
import br.com.ciccr.simo.modules.attendance.dto.response.AttendanceResponse;
import br.com.ciccr.simo.modules.attendance.enums.AttendanceAction;
import br.com.ciccr.simo.modules.attendance.enums.AttendanceStatus;
import br.com.ciccr.simo.modules.attendance.mapper.AttendanceMapper;
import br.com.ciccr.simo.modules.attendance.mapper.AttendanceSnapshotMapper;
import br.com.ciccr.simo.modules.attendance.model.Attendance;
import br.com.ciccr.simo.modules.attendance.model.AttendanceHistory;
import br.com.ciccr.simo.modules.attendance.model.AttendanceType;
import br.com.ciccr.simo.modules.attendance.repository.AttendanceHistoryRepository;
import br.com.ciccr.simo.modules.attendance.repository.AttendanceRepository;
import br.com.ciccr.simo.modules.attendance.repository.AttendanceTypeRepository;
import br.com.ciccr.simo.modules.attendance.snapshot.AttendanceSnapshot;
import br.com.ciccr.simo.modules.attendance.validator.AttendanceValidator;
import br.com.ciccr.simo.modules.force.model.Force;
import br.com.ciccr.simo.modules.force.repository.ForceRepository;
import br.com.ciccr.simo.modules.user.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AttendanceServiceImpl extends BaseService<Attendance, Long> implements AttendanceService {

    private final AttendanceRepository repository;
    private final AttendanceMapper mapper;
    private final AttendanceValidator validator;
    private final AttendanceHistoryService historyService;
    private final ForceRepository forceRepository;
    private final AttendanceTypeRepository attendanceTypeRepository;
    private final CurrentUserService currentUserService;
    private final ProtocolGenerator protocolGenerator;

    @Override
    protected JpaRepository<Attendance, Long> repository() {
        return repository;
    }

    @Override
    public AttendanceResponse create(CreateAttendanceRequest request) {

        Force force = validator.validateForce(request.forceId());

        List<AttendanceType> types = validator.validateAttendanceTypes(request.attendanceTypeIds());

        User loggedUser = currentUserService.getLoggedUser();

        Attendance attendance = mapper.toEntity(request);

        attendance.setForce(force);
        attendance.setAttendanceTypes(types);
        attendance.setCreatedUser(loggedUser);
        attendance.setAttendanceDateTime(LocalDateTime.now());
        attendance.setStatus(AttendanceStatus.OPEN);
        attendance.setVersion(1);
        attendance.setActive(true);
        attendance = save(attendance);

        attendance.setProtocol(protocolGenerator.generate(
                        "ATD",
                        attendance.getId()
                )
        );

        attendance = save(attendance);

        historyService.registerHistory(
                attendance,
                AttendanceAction.CREATED,
                "Atendimento criado."
        );

        return mapper.toResponse(attendance);

    }

}