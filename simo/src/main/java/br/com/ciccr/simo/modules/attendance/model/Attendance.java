package br.com.ciccr.simo.modules.attendance.model;

import br.com.ciccr.simo.common.model.BaseEntity;
import br.com.ciccr.simo.modules.attendance.enums.AttendanceAction;
import br.com.ciccr.simo.modules.attendance.enums.AttendanceStatus;
import br.com.ciccr.simo.modules.force.model.Force;
import br.com.ciccr.simo.modules.user.model.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "attendances")
@Getter
@Setter
public class Attendance extends BaseEntity {

    @Column(name = "attendance_date_time", nullable = false)
    private LocalDateTime attendanceDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "force_id", nullable = false)
    private Force force;

    @ManyToMany
    @JoinTable(
            name = "attendance_attendance_types",
            joinColumns = @JoinColumn(name = "attendance_id"),
            inverseJoinColumns = @JoinColumn(name = "attendance_type_id")
    )
    private List<AttendanceType> attendanceTypes = new ArrayList<>();

    @Column(length = 150)
    private String server;

    @Column(length = 150)
    private String requester;

    @Column(length = 150)
    private String institution;

    @Column(length = 100)
    private String unit;

    @Column(length = 100)
    private String subUnit;

    private Integer arrests;

    @Column(length = 50)
    private String occurrenceNumber;

    @Column(nullable = false, unique = true, length = 30)
    private String protocol;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String observation;

    @Column(nullable = false)
    private boolean active = true;

    @Column(nullable = false)
    private Integer version = 1;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AttendanceStatus status = AttendanceStatus.OPEN;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_user_id", nullable = false)
    private User createdUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "closed_by")
    private User closedBy;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private AttendanceAction action;

    private LocalDateTime closedAt;

}