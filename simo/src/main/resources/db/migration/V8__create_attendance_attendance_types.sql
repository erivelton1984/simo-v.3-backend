CREATE TABLE attendance_attendance_type
(
    attendance_id BIGINT NOT NULL,

    attendance_type_id BIGINT NOT NULL,

    PRIMARY KEY (attendance_id, attendance_type_id),

    CONSTRAINT fk_attendance
        FOREIGN KEY (attendance_id)
            REFERENCES attendances(id),

    CONSTRAINT fk_attendance_type
        FOREIGN KEY (attendance_type_id)
            REFERENCES attendance_type(id)
);