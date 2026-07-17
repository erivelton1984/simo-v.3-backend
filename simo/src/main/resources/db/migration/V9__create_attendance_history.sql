CREATE TABLE attendance_history
(
    id BIGSERIAL PRIMARY KEY,

    attendance_id BIGINT NOT NULL,

    version INTEGER NOT NULL,

    action VARCHAR(30) NOT NULL,

    description TEXT,

    snapshot TEXT NOT NULL,

    created_at TIMESTAMP,

    updated_at TIMESTAMP,

    created_by VARCHAR(100),

    updated_by VARCHAR(100),

    CONSTRAINT fk_history_attendance
        FOREIGN KEY (attendance_id)
            REFERENCES attendances(id)
);

CREATE INDEX idx_attendance_history_attendance
    ON attendance_history(attendance_id);

CREATE INDEX idx_attendance_history_version
    ON attendance_history(attendance_id, version);

CREATE INDEX idx_attendance_history_created_at
    ON attendance_history(created_at);