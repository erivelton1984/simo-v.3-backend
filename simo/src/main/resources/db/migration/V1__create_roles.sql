CREATE TABLE roles
(
    id BIGSERIAL PRIMARY KEY,

    name VARCHAR(30) NOT NULL UNIQUE,

    description VARCHAR(255),

    created_at TIMESTAMP,

    updated_at TIMESTAMP,

    created_by VARCHAR(100),

    updated_by VARCHAR(100)
);