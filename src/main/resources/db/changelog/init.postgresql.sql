-- liquibase formatted sql

-- changeset init:insert-default-roles
INSERT INTO roles (role)
SELECT 'USER'
WHERE NOT EXISTS (SELECT 1 FROM roles WHERE role = 'USER');

INSERT INTO roles (role)
SELECT 'ADMIN'
WHERE NOT EXISTS (SELECT 1 FROM roles WHERE role = 'ADMIN');