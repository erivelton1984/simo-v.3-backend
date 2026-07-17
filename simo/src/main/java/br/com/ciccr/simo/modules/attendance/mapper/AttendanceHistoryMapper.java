package br.com.ciccr.simo.modules.attendance.mapper;

import br.com.ciccr.simo.modules.attendance.dto.response.AttendanceHistoryResponse;
import br.com.ciccr.simo.modules.attendance.model.AttendanceHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AttendanceHistoryMapper {

    @Mapping(target = "attendanceId", source = "attendance.id")
    AttendanceHistoryResponse toResponse(AttendanceHistory entity);

    List<AttendanceHistoryResponse> toResponse(List<AttendanceHistory> entities);

}