package br.com.ciccr.simo.modules.attendance.validator;

import br.com.ciccr.simo.common.exception.BusinessException;
import br.com.ciccr.simo.common.exception.ResourceNotFoundException;
import br.com.ciccr.simo.modules.attendance.constants.AttendanceMessages;
import br.com.ciccr.simo.modules.attendance.enums.AttendanceStatus;
import br.com.ciccr.simo.modules.attendance.model.Attendance;
import br.com.ciccr.simo.modules.attendance.model.AttendanceType;
import br.com.ciccr.simo.modules.attendance.repository.AttendanceRepository;
import br.com.ciccr.simo.modules.attendance.repository.AttendanceTypeRepository;
import br.com.ciccr.simo.modules.force.constants.ForceMessages;
import br.com.ciccr.simo.modules.force.model.Force;
import br.com.ciccr.simo.modules.force.repository.ForceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AttendanceValidator {

    private final AttendanceRepository attendanceRepository;
    private final ForceRepository forceRepository;
    private final AttendanceTypeRepository attendanceTypeRepository;

    public Attendance findAttendanceOrThrow(Long id) {
        return attendanceRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                AttendanceMessages.NOT_FOUND
                        ));
    }

    public Force validateForce(Long id) {
        return forceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ForceMessages.NOT_FOUND));
    }

    public List<AttendanceType> validateAttendanceTypes(List<Long> ids) {
        List<AttendanceType> types = attendanceTypeRepository.findAllById(ids);

        if (types.size() != ids.size()) {
            throw new ResourceNotFoundException(
                    "Um ou mais tipos de atendimento não foram encontrados."
            );
        }
        return types;
    }

    public void validateEdition(Attendance attendance) {

        if (attendance.getStatus() == AttendanceStatus.CANCELLED) {
            throw new BusinessException(AttendanceMessages.CANCELED_CANNOT_BE_UPDATED);
        }
    }

    public void validateClose(Attendance attendance) {

        if (attendance.getStatus() == AttendanceStatus.CLOSED) {
            throw new BusinessException(AttendanceMessages.ALREADY_CLOSED);
        }
    }

    public void validateReopen(Attendance attendance) {

        if (attendance.getStatus() != AttendanceStatus.CLOSED) {
            throw new BusinessException(
                    "Somente atendimentos encerrados podem ser reabertos."
            );
        }
    }




}
