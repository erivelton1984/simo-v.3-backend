package br.com.ciccr.simo.modules.attendance.mapper;

import br.com.ciccr.simo.modules.attendance.dto.response.AttendanceTypeResponse;
import br.com.ciccr.simo.modules.attendance.model.AttendanceType;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AttendanceTypeMapper {

    AttendanceTypeResponse toResponse(AttendanceType entity);

    List<AttendanceTypeResponse> toResponse(List<AttendanceType> entities);

}