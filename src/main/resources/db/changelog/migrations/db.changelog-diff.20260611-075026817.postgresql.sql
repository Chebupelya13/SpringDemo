-- liquibase formatted sql

-- changeset butt:1781164233785-7 splitStatements:false
ALTER TABLE photos ADD photo BYTEA;

-- changeset butt:1781164233785-8 splitStatements:false
ALTER TABLE user_roles DROP CONSTRAINT user_roles_pkey;

-- changeset butt:1781164233785-1 splitStatements:false
ALTER TABLE users ALTER COLUMN marital_status TYPE VARCHAR(255) USING (marital_status::VARCHAR(255));

-- changeset butt:1781164233785-2 splitStatements:false
ALTER TABLE roles ALTER COLUMN role TYPE VARCHAR(255) USING (role::VARCHAR(255));

-- changeset butt:1781164233785-3 splitStatements:false
ALTER TABLE roles ALTER COLUMN  role DROP NOT NULL;

-- changeset butt:1781164233785-4 splitStatements:false
ALTER TABLE agreements ALTER COLUMN status TYPE VARCHAR(255) USING (status::VARCHAR(255));

-- changeset butt:1781164233785-5 splitStatements:false
ALTER TABLE applications ALTER COLUMN status TYPE VARCHAR(255) USING (status::VARCHAR(255));

-- changeset butt:1781164233785-6 splitStatements:false
ALTER TABLE photos ALTER COLUMN type TYPE VARCHAR(255) USING (type::VARCHAR(255));

