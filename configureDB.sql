CREATE USER bank_user WITH PASSWORD '1234';
CREATE DATABASE bankdb OWNER bank_user;

\c bankdb;

GRANT ALL PRIVILEGES ON DATABASE bankdb TO bank_user;
GRANT ALL ON SCHEMA public TO bank_user;

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    firstname VARCHAR(255),
    surname VARCHAR(255),
    patronymic VARCHAR(255),
    birthday DATE,
    passport VARCHAR(50),
    address TEXT,
    marital_status VARCHAR(50) CHECK (marital_status IN ('MARRIED', 'NOT_MARRIED')),
    phone_number VARCHAR(50)
);

CREATE TABLE employment_periods (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL REFERENCES users(id),
    since DATE,
    up_to DATE,
    company_title VARCHAR(255),
    CONSTRAINT unique_user_employment UNIQUE (user_id)
);

CREATE TABLE applications (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL REFERENCES users(id),
    status VARCHAR(50) DEFAULT 'IN_PROGRESS' CHECK (status IN ('ACCEPTED', 'DECLINED', 'IN_PROGRESS')),
    amount INTEGER NOT NULL,
    term_months INTEGER
);

CREATE TABLE agreements (
    id SERIAL PRIMARY KEY,
    application_id INT NOT NULL REFERENCES applications(id),
    user_id INT NOT NULL REFERENCES users(id),
    status VARCHAR(50) DEFAULT 'WAITING_TO_SIGN' CHECK (status IN ('SIGNED', 'WAITING_TO_SIGN'))
);

GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO bank_user;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO bank_user;
