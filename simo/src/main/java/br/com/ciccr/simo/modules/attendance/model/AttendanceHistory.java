package br.com.ciccr.simo.modules.attendance.model;

import br.com.ciccr.simo.common.model.BaseEntity;
import br.com.ciccr.simo.modules.attendance.enums.AttendanceAction;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "attendance_history")
@Getter
@Setter
public class AttendanceHistory extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attendance_id", nullable = false)
    private Attendance attendance;

    @Column(nullable = false)
    private Integer version;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private AttendanceAction action;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String snapshot;

}