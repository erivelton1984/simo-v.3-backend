CREATE TABLE users
(
    id BIGSERIAL PRIMARY KEY,

    full_name VARCHAR(150) NOT NULL,

    cpf VARCHAR(15) NOT NULL,

    registration VARCHAR(150) NOT NULL,

    phone VARCHAR(15) NOT NULL,

    position VARCHAR(20) NOT NULL,

    email VARCHAR(150) NOT NULL UNIQUE,

    password VARCHAR(255) NOT NULL,

    active BOOLEAN NOT NULL DEFAULT TRUE,

    role_id BIGINT NOT NULL,

    created_at TIMESTAMP,

    updated_at TIMESTAMP,

    created_by VARCHAR(100),

    updated_by VARCHAR(100),

    CONSTRAINT fk_user_role
        FOREIGN KEY (role_id)
            REFERENCES roles(id)
);