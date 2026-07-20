CREATE TABLE attendances
(
    id BIGSERIAL PRIMARY KEY,

    attendance_date_time TIMESTAMP NOT NULL,

    force_id BIGINT NOT NULL,

    created_user_id BIGINT NOT NULL,

    server VARCHAR(150),

    requester VARCHAR(150),

    institution VARCHAR(150),

    unit VARCHAR(100),

    sub_unit VARCHAR(100),

    occurrence_number VARCHAR(50),

    protocol VARCHAR(30) NOT NULL UNIQUE,

    arrests INTEGER,

    observation TEXT,

    active BOOLEAN NOT NULL DEFAULT TRUE,

    action VARCHAR(30) NOT NULL DEFAULT 'CREATED',

    version INTEGER NOT NULL DEFAULT 1,

    status VARCHAR(20) NOT NULL DEFAULT 'OPEN',

    closed_by BIGINT,

    closed_at TIMESTAMP,

    created_at TIMESTAMP,

    updated_at TIMESTAMP,

    created_by VARCHAR(100),

    updated_by VARCHAR(100),

    CONSTRAINT fk_attendance_force
        FOREIGN KEY (force_id)
            REFERENCES forces(id),

    CONSTRAINT fk_attendance_created_user
        FOREIGN KEY (created_user_id)
            REFERENCES users(id),

    CONSTRAINT fk_attendance_closed_by
        FOREIGN KEY (closed_by)
            REFERENCES users(id)
);

CREATE INDEX idx_attendance_protocol
    ON attendances(protocol);

CREATE INDEX idx_attendance_datetime
    ON attendances(attendance_date_time);

CREATE INDEX idx_attendance_force
    ON attendances(force_id);

CREATE INDEX idx_attendance_status
    ON attendances(status);

CREATE INDEX idx_attendance_created_user
    ON attendances(created_user_id);