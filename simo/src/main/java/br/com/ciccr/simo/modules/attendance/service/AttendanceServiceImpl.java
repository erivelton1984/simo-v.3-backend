package br.com.ciccr.simo.modules.attendance.service;

import br.com.ciccr.simo.auth.current.CurrentUserService;
import br.com.ciccr.simo.common.service.BaseService;
import br.com.ciccr.simo.common.util.ProtocolGenerator;
import br.com.ciccr.simo.modules.attendance.dto.request.AttendanceFilterRequest;
import br.com.ciccr.simo.modules.attendance.dto.request.CreateAttendanceRequest;
import br.com.ciccr.simo.modules.attendance.dto.request.UpdateAttendanceRequest;
import br.com.ciccr.simo.modules.attendance.dto.response.AttendanceResponse;
import br.com.ciccr.simo.modules.attendance.dto.response.AttendanceSummaryResponse;
import br.com.ciccr.simo.modules.attendance.enums.AttendanceAction;
import br.com.ciccr.simo.modules.attendance.enums.AttendanceStatus;
import br.com.ciccr.simo.modules.attendance.mapper.AttendanceMapper;
import br.com.ciccr.simo.modules.attendance.model.Attendance;
import br.com.ciccr.simo.modules.attendance.model.AttendanceType;
import br.com.ciccr.simo.modules.attendance.repository.AttendanceRepository;
import br.com.ciccr.simo.modules.attendance.validator.AttendanceValidator;
import br.com.ciccr.simo.modules.force.model.Force;
import br.com.ciccr.simo.modules.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.com.ciccr.simo.modules.attendance.specification.AttendanceSpecification;


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
    private final CurrentUserService currentUserService;
    private final ProtocolGenerator protocolGenerator;

    @Override
    protected JpaRepository<Attendance, Long> repository() {
        return repository;
    }

    @Override
    public AttendanceResponse create(CreateAttendanceRequest request) {

        Force force = validator.validateForce(request.forceId());

        List<AttendanceType> attendanceTypes =
                validator.validateAttendanceTypes(request.attendanceTypeIds());

        User loggedUser = currentUserService.getCurrentUser();

        Attendance attendance = mapper.toEntity(request);

        buildAttendance(
                attendance,
                request,
                force,
                attendanceTypes,
                loggedUser
        );

        attendance = save(attendance);

        attendance = generateProtocol(attendance);

        historyService.registerHistory(
                attendance,
                AttendanceAction.CREATED,
                "Atendimento criado."
        );

        return mapper.toResponse(attendance);
    }

    @Override
    @Transactional(readOnly = true)
    public AttendanceResponse findById(Long id) {

        Attendance attendance =
                validator.findAttendanceOrThrow(id);

        return mapper.toResponse(attendance);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AttendanceSummaryResponse> findAll(AttendanceFilterRequest filter, Pageable pageable) {
        return repository.findAll(AttendanceSpecification.byFilter(filter), pageable)
                .map(mapper::toSummaryResponse);
    }

    @Override
    public AttendanceResponse update(Long id, UpdateAttendanceRequest request) {

        Attendance attendance =
                validator.findAttendanceOrThrow(id);

        validator.validateEdition(attendance);

        Force force =
                validator.validateForce(request.forceId());

        List<AttendanceType> attendanceTypes =
                validator.validateAttendanceTypes(request.attendanceTypeIds());

        mapper.update(request, attendance);

        attendance.setForce(force);
        attendance.setAttendanceTypes(attendanceTypes);
        attendance.setVersion(attendance.getVersion() + 1);

        attendance = save(attendance);

        historyService.registerHistory(
                attendance,
                AttendanceAction.UPDATED,
                "Atendimento atualizado."
        );

        return mapper.toResponse(attendance);
    }

    @Override
    public void close(Long id) {

        Attendance attendance =
                validator.findAttendanceOrThrow(id);

        validator.validateClose(attendance);

        attendance.setStatus(AttendanceStatus.CLOSED);
        attendance.setClosedAt(LocalDateTime.now());
        attendance.setClosedBy(currentUserService.getCurrentUser());

        attendance = save(attendance);

        historyService.registerHistory(
                attendance,
                AttendanceAction.CLOSED,
                "Atendimento encerrado."
        );
    }

    @Override
    public void reopen(Long id) {

        Attendance attendance =
                validator.findAttendanceOrThrow(id);

        validator.validateReopen(attendance);

        attendance.setStatus(AttendanceStatus.OPEN);
        attendance.setClosedAt(null);
        attendance.setClosedBy(null);

        attendance = save(attendance);

        historyService.registerHistory(
                attendance,
                AttendanceAction.REOPENED,
                "Atendimento reaberto."
        );
    }

    @Override
    public void delete(Long id) {

        Attendance attendance =
                validator.findAttendanceOrThrow(id);

        attendance.setActive(false);

        attendance = save(attendance);

        historyService.registerHistory(
                attendance,
                AttendanceAction.DEACTIVATED,
                "Atendimento desativado."
        );
    }

    private void buildAttendance(
            Attendance attendance,
            CreateAttendanceRequest request,
            Force force,
            List<AttendanceType> attendanceTypes,
            User loggedUser) {

        attendance.setForce(force);
        attendance.setAttendanceTypes(attendanceTypes);
        attendance.setCreatedUser(loggedUser);
        attendance.setAttendanceDateTime(request.attendanceDateTime());
        attendance.setStatus(AttendanceStatus.OPEN);
        attendance.setVersion(1);
        attendance.setActive(true);
    }

    private Attendance generateProtocol(Attendance attendance) {

        attendance.setProtocol(
                protocolGenerator.generate(
                        "ATD",
                        attendance.getId()
                )
        );

        return save(attendance);
    }

}