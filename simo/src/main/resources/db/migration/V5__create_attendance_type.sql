CREATE TABLE attendance_types
(
    id BIGSERIAL PRIMARY KEY,

    name VARCHAR(100) NOT NULL UNIQUE,

    active BOOLEAN NOT NULL DEFAULT TRUE,

    created_at TIMESTAMP,

    updated_at TIMESTAMP,

    created_by VARCHAR(100),

    updated_by VARCHAR(100)
);