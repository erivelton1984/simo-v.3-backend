package br.com.ciccr.simo.modules.attendance.mapper;

import br.com.ciccr.simo.modules.attendance.dto.request.CreateAttendanceRequest;
import br.com.ciccr.simo.modules.attendance.dto.request.UpdateAttendanceRequest;
import br.com.ciccr.simo.modules.attendance.dto.response.AttendanceResponse;
import br.com.ciccr.simo.modules.attendance.dto.response.AttendanceSummaryResponse;
import br.com.ciccr.simo.modules.attendance.model.Attendance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = AttendanceTypeMapper.class)
public interface AttendanceMapper {

    @Mapping(target = "forceId", source = "force.id")
    @Mapping(target = "forceName", source = "force.name")
    @Mapping(target = "createdUser", source = "createdUser.fullName")
    @Mapping(target = "closedBy", source = "closedBy.fullName")
    AttendanceResponse toResponse(Attendance entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "force", ignore = true)
    @Mapping(target = "attendanceTypes", ignore = true)
    @Mapping(target = "protocol", ignore = true)
    @Mapping(target = "attendanceDateTime", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "createdUser", ignore = true)
    @Mapping(target = "closedBy", ignore = true)
    @Mapping(target = "closedAt", ignore = true)
    Attendance toEntity(CreateAttendanceRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "force", ignore = true)
    @Mapping(target = "attendanceTypes", ignore = true)
    @Mapping(target = "protocol", ignore = true)
    @Mapping(target = "attendanceDateTime", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "createdUser", ignore = true)
    @Mapping(target = "closedBy", ignore = true)
    @Mapping(target = "closedAt", ignore = true)
    void update(UpdateAttendanceRequest request, @MappingTarget Attendance attendance);

    @Mapping(target = "forceName", source = "force.name")
    @Mapping(target = "createdUser", source = "createdUser.fullName")
    AttendanceSummaryResponse toSummaryResponse(Attendance entity);

    List<AttendanceSummaryResponse> toSummaryResponse(List<Attendance> entities);

}