package br.com.ciccr.simo.modules.attendance.mapper;

import br.com.ciccr.simo.modules.attendance.model.Attendance;
import br.com.ciccr.simo.modules.attendance.snapshot.AttendanceSnapshot;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        imports = java.util.stream.Collectors.class
)
public interface AttendanceSnapshotMapper {

    @Mapping(target = "force", source = "force.name")

    @Mapping(target = "createdUser", source = "createdUser.fullName")

    @Mapping(target = "closedBy", source = "closedBy.fullName")

    @Mapping(
            target = "attendanceTypes",
            expression = "java(attendance.getAttendanceTypes().stream().map(type -> type.getName()).collect(Collectors.toList()))"
    )
    AttendanceSnapshot toSnapshot(Attendance attendance);

}