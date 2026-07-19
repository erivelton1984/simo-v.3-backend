package br.com.ciccr.simo.modules.attendance.service;

import br.com.ciccr.simo.common.exception.BusinessException;
import br.com.ciccr.simo.modules.attendance.constants.AttendanceMessages;
import br.com.ciccr.simo.modules.attendance.enums.AttendanceAction;
import br.com.ciccr.simo.modules.attendance.mapper.AttendanceSnapshotMapper;
import br.com.ciccr.simo.modules.attendance.model.Attendance;
import br.com.ciccr.simo.modules.attendance.model.AttendanceHistory;
import br.com.ciccr.simo.modules.attendance.repository.AttendanceHistoryRepository;
import br.com.ciccr.simo.modules.attendance.snapshot.AttendanceSnapshot;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class AttendanceHistoryServiceImpl
        implements AttendanceHistoryService {

    private final AttendanceHistoryRepository repository;

    private final AttendanceSnapshotMapper snapshotMapper;

    private final ObjectMapper objectMapper;

    @Override
    public void registerHistory(
            Attendance attendance,
            AttendanceAction action,
            String description) {

        AttendanceSnapshot snapshot =
                snapshotMapper.toSnapshot(attendance);

        String json;

        try {

            json = objectMapper.writeValueAsString(snapshot);

        } catch (JsonProcessingException e) {

            throw new BusinessException(
                    AttendanceMessages.SNAPSHOT_ERROR);

        }

        AttendanceHistory history = new AttendanceHistory();

        history.setAttendance(attendance);
        history.setVersion(attendance.getVersion());
        history.setAction(action);
        history.setDescription(description);
        history.setSnapshot(json);

        repository.save(history);
    }
}