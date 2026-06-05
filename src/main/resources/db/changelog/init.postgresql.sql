-- liquibase formatted sql

-- changeset init:create-users-table
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    firstname VARCHAR(255) NOT NULL,
    surname VARCHAR(255) NOT NULL,
    patronymic VARCHAR(255),
    birthday TIMESTAMP NOT NULL,
    passport_series INT NOT NULL,
    passport_number INT NOT NULL,
    address VARCHAR(255) NOT NULL,
    marital_status VARCHAR(50) NOT NULL,
    phone_number VARCHAR(255) NOT NULL
);

-- changeset init:create-roles-table
CREATE TABLE roles (
    id SERIAL PRIMARY KEY,
    role VARCHAR(50) NOT NULL
);

-- changeset init:create-user_roles-table
CREATE TABLE user_roles (
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_user_roles_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk_user_roles_role FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE CASCADE
);

-- changeset init:create-applications-table
CREATE TABLE applications (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    status VARCHAR(50) NOT NULL,
    amount INT NOT NULL,
    term_months integer check (term_months >= 1 and term_months <= 12) NOT NULL,
    CONSTRAINT fk_applications_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

-- changeset init:create-agreements-table
CREATE TABLE agreements (
    id SERIAL PRIMARY KEY,
    application_id INT NOT NULL UNIQUE,
    status VARCHAR(50) NOT NULL,
    CONSTRAINT fk_agreements_application FOREIGN KEY (application_id) REFERENCES applications (id) ON DELETE CASCADE
);

-- changeset init:create-photos-table
CREATE TABLE photos (
    id SERIAL PRIMARY KEY,
    path VARCHAR(255),
    type VARCHAR(50),
    user_id INT,
    application_id INT,
    CONSTRAINT fk_photos_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk_photos_application FOREIGN KEY (application_id) REFERENCES applications (id) ON DELETE CASCADE
);

-- changeset init:create-employment_periods-table
CREATE TABLE employment_periods (
    id INT PRIMARY KEY,
    user_id INT,
    since TIMESTAMP,
    up_to TIMESTAMP,
    company_title VARCHAR(255),
    CONSTRAINT fk_employment_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

-- changeset init:insert-default-roles
INSERT INTO roles (role)
SELECT 'USER'
WHERE NOT EXISTS (SELECT 1 FROM roles WHERE role = 'USER');

INSERT INTO roles (role)
SELECT 'ADMIN'
WHERE NOT EXISTS (SELECT 1 FROM roles WHERE role = 'ADMIN');