-- liquibase formatted sql logicalFilePath:db/changelog/master-changelog.xml

-- changeset User1 (generated):1780297608201-1
CREATE TABLE IF NOT EXISTS agreements (id INTEGER NOT NULL, status VARCHAR(255) NOT NULL, application_id INTEGER NOT NULL, CONSTRAINT agreements_pkey PRIMARY KEY (id));

-- changeset User1 (generated):1780297608201-2
CREATE TABLE IF NOT EXISTS applications (id INTEGER NOT NULL, amount INTEGER NOT NULL, status VARCHAR(255) NOT NULL, term_months INTEGER NOT NULL, user_id INTEGER NOT NULL, CONSTRAINT applications_pkey PRIMARY KEY (id));

-- changeset User1 (generated):1780297608201-3
CREATE TABLE IF NOT EXISTS roles (id INTEGER NOT NULL, role VARCHAR(255), CONSTRAINT roles_pkey PRIMARY KEY (id));

-- changeset User1 (generated):1780297608201-4
CREATE TABLE IF NOT EXISTS users (id INTEGER NOT NULL, address VARCHAR(255) NOT NULL, birthday TIMESTAMP WITHOUT TIME ZONE NOT NULL, firstname VARCHAR(255) NOT NULL, marital_status VARCHAR(255) NOT NULL, passport_number INTEGER NOT NULL, passport_series INTEGER NOT NULL, password VARCHAR(255), patronymic VARCHAR(255), phone_number VARCHAR(255) NOT NULL, surname VARCHAR(255) NOT NULL, username VARCHAR(255) NOT NULL, role INTEGER, CONSTRAINT users_pkey PRIMARY KEY (id));

-- changeset User1 (generated):1780297608201-5
CREATE TABLE IF NOT EXISTS employment_periods (id INTEGER NOT NULL, company_title VARCHAR(255), since TIMESTAMP WITHOUT TIME ZONE, up_to TIMESTAMP WITHOUT TIME ZONE, user_id INTEGER, CONSTRAINT employment_periods_pkey PRIMARY KEY (id));

-- changeset User1 (generated):1780297608201-6
-- preconditions onFail:MARK_RAN onError:MARK_RAN
-- precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM pg_constraint WHERE conname = 'ukctles8a8t3bmswf99q3ns8sa0'
ALTER TABLE agreements ADD CONSTRAINT ukctles8a8t3bmswf99q3ns8sa0 UNIQUE (application_id);

-- changeset User1 (generated):1780297608201-7
-- preconditions onFail:MARK_RAN onError:MARK_RAN
-- precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM pg_constraint WHERE conname = 'ukr43af9ap4edm43mmtq01oddj6'
ALTER TABLE users ADD CONSTRAINT ukr43af9ap4edm43mmtq01oddj6 UNIQUE (username);

-- changeset User1 (generated):1780297608201-8
CREATE SEQUENCE IF NOT EXISTS agreements_seq AS bigint START WITH 1 INCREMENT BY 50 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1;

-- changeset User1 (generated):1780297608201-9
CREATE SEQUENCE IF NOT EXISTS applications_seq AS bigint START WITH 1 INCREMENT BY 50 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1;

-- changeset User1 (generated):1780297608201-10
CREATE SEQUENCE IF NOT EXISTS role_seq AS bigint START WITH 1 INCREMENT BY 50 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1;

-- changeset User1 (generated):1780297608201-11
CREATE SEQUENCE IF NOT EXISTS roles_seq AS bigint START WITH 1 INCREMENT BY 50 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1;

-- changeset User1 (generated):1780297608201-12
CREATE SEQUENCE IF NOT EXISTS users_seq AS bigint START WITH 1 INCREMENT BY 50 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1;

-- changeset User1 (generated):1780297608201-13
-- preconditions onFail:MARK_RAN onError:MARK_RAN
-- precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM pg_constraint WHERE conname = 'fk4c6vlshk8x83ifeoggi3exg3k'
ALTER TABLE users ADD CONSTRAINT fk4c6vlshk8x83ifeoggi3exg3k FOREIGN KEY (role) REFERENCES roles (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

-- changeset User1 (generated):1780297608201-14
-- preconditions onFail:MARK_RAN onError:MARK_RAN
-- precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM pg_constraint WHERE conname = 'fk80yimupy4rd72plmnkgi6fs1'
ALTER TABLE employment_periods ADD CONSTRAINT fk80yimupy4rd72plmnkgi6fs1 FOREIGN KEY (user_id) REFERENCES users (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

-- changeset User1 (generated):1780297608201-15
-- preconditions onFail:MARK_RAN onError:MARK_RAN
-- precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM pg_constraint WHERE conname = 'fkbia4qv7rikhu2ymx9died7irk'
ALTER TABLE agreements ADD CONSTRAINT fkbia4qv7rikhu2ymx9died7irk FOREIGN KEY (application_id) REFERENCES applications (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

-- changeset User1 (generated):1780297608201-16
-- preconditions onFail:MARK_RAN onError:MARK_RAN
-- precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM pg_constraint WHERE conname = 'fkfsfqljedcla632u568jl5qf3w'
ALTER TABLE applications ADD CONSTRAINT fkfsfqljedcla632u568jl5qf3w FOREIGN KEY (user_id) REFERENCES users (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

-- changeset User1 (generated):1780303094543-1
ALTER TABLE users DROP COLUMN IF EXISTS test_field_delme;