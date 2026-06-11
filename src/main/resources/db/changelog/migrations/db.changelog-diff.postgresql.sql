-- changeset butt:1780570923647-1 splitStatements:false
ALTER TABLE users ADD email VARCHAR(255);

-- changeset butt:1780571150863-1 splitStatements:false
ALTER TABLE users DROP COLUMN email;

-- changeset butt:1781163066278-7 splitStatements:false
ALTER TABLE photos ADD photo BYTEA;

-- changeset butt:1781163066278-8 splitStatements:false
ALTER TABLE user_roles DROP CONSTRAINT user_roles_pkey;

-- changeset butt:1781163066278-1 splitStatements:false
ALTER TABLE users ALTER COLUMN marital_status TYPE VARCHAR(255) USING (marital_status::VARCHAR(255));

-- changeset butt:1781163066278-2 splitStatements:false
ALTER TABLE roles ALTER COLUMN role TYPE VARCHAR(255) USING (role::VARCHAR(255));

-- changeset butt:1781163066278-3 splitStatements:false
ALTER TABLE roles ALTER COLUMN  role DROP NOT NULL;

-- changeset butt:1781163066278-4 splitStatements:false
ALTER TABLE agreements ALTER COLUMN status TYPE VARCHAR(255) USING (status::VARCHAR(255));

-- changeset butt:1781163066278-5 splitStatements:false
ALTER TABLE applications ALTER COLUMN status TYPE VARCHAR(255) USING (status::VARCHAR(255));

-- changeset butt:1781163066278-6 splitStatements:false
ALTER TABLE photos ALTER COLUMN type TYPE VARCHAR(255) USING (type::VARCHAR(255));

-- changeset butt:1781163283236-7 splitStatements:false
ALTER TABLE photos ADD photo BYTEA;

-- changeset butt:1781163283236-8 splitStatements:false
ALTER TABLE user_roles DROP CONSTRAINT user_roles_pkey;

-- changeset butt:1781163283236-1 splitStatements:false
ALTER TABLE users ALTER COLUMN marital_status TYPE VARCHAR(255) USING (marital_status::VARCHAR(255));

-- changeset butt:1781163283236-2 splitStatements:false
ALTER TABLE roles ALTER COLUMN role TYPE VARCHAR(255) USING (role::VARCHAR(255));

-- changeset butt:1781163283236-3 splitStatements:false
ALTER TABLE roles ALTER COLUMN  role DROP NOT NULL;

-- changeset butt:1781163283236-4 splitStatements:false
ALTER TABLE agreements ALTER COLUMN status TYPE VARCHAR(255) USING (status::VARCHAR(255));

-- changeset butt:1781163283236-5 splitStatements:false
ALTER TABLE applications ALTER COLUMN status TYPE VARCHAR(255) USING (status::VARCHAR(255));

-- changeset butt:1781163283236-6 splitStatements:false
ALTER TABLE photos ALTER COLUMN type TYPE VARCHAR(255) USING (type::VARCHAR(255));

-- changeset butt:1781163314108-7 splitStatements:false
ALTER TABLE photos ADD photo BYTEA;

-- changeset butt:1781163314108-8 splitStatements:false
ALTER TABLE user_roles DROP CONSTRAINT user_roles_pkey;

-- changeset butt:1781163314108-1 splitStatements:false
ALTER TABLE users ALTER COLUMN marital_status TYPE VARCHAR(255) USING (marital_status::VARCHAR(255));

-- changeset butt:1781163314108-2 splitStatements:false
ALTER TABLE roles ALTER COLUMN role TYPE VARCHAR(255) USING (role::VARCHAR(255));

-- changeset butt:1781163314108-3 splitStatements:false
ALTER TABLE roles ALTER COLUMN  role DROP NOT NULL;

-- changeset butt:1781163314108-4 splitStatements:false
ALTER TABLE agreements ALTER COLUMN status TYPE VARCHAR(255) USING (status::VARCHAR(255));

-- changeset butt:1781163314108-5 splitStatements:false
ALTER TABLE applications ALTER COLUMN status TYPE VARCHAR(255) USING (status::VARCHAR(255));

-- changeset butt:1781163314108-6 splitStatements:false
ALTER TABLE photos ALTER COLUMN type TYPE VARCHAR(255) USING (type::VARCHAR(255));

-- changeset butt:1781163534830-7 splitStatements:false
ALTER TABLE photos ADD photo BYTEA;

-- changeset butt:1781163534830-8 splitStatements:false
ALTER TABLE user_roles DROP CONSTRAINT user_roles_pkey;

-- changeset butt:1781163534830-1 splitStatements:false
ALTER TABLE users ALTER COLUMN marital_status TYPE VARCHAR(255) USING (marital_status::VARCHAR(255));

-- changeset butt:1781163534830-2 splitStatements:false
ALTER TABLE roles ALTER COLUMN role TYPE VARCHAR(255) USING (role::VARCHAR(255));

-- changeset butt:1781163534830-3 splitStatements:false
ALTER TABLE roles ALTER COLUMN  role DROP NOT NULL;

-- changeset butt:1781163534830-4 splitStatements:false
ALTER TABLE agreements ALTER COLUMN status TYPE VARCHAR(255) USING (status::VARCHAR(255));

-- changeset butt:1781163534830-5 splitStatements:false
ALTER TABLE applications ALTER COLUMN status TYPE VARCHAR(255) USING (status::VARCHAR(255));

-- changeset butt:1781163534830-6 splitStatements:false
ALTER TABLE photos ALTER COLUMN type TYPE VARCHAR(255) USING (type::VARCHAR(255));

-- changeset butt:1781163597499-7 splitStatements:false
ALTER TABLE photos ADD photo BYTEA;

-- changeset butt:1781163597499-8 splitStatements:false
ALTER TABLE user_roles DROP CONSTRAINT user_roles_pkey;

-- changeset butt:1781163597499-1 splitStatements:false
ALTER TABLE users ALTER COLUMN marital_status TYPE VARCHAR(255) USING (marital_status::VARCHAR(255));

-- changeset butt:1781163597499-2 splitStatements:false
ALTER TABLE roles ALTER COLUMN role TYPE VARCHAR(255) USING (role::VARCHAR(255));

-- changeset butt:1781163597499-3 splitStatements:false
ALTER TABLE roles ALTER COLUMN  role DROP NOT NULL;

-- changeset butt:1781163597499-4 splitStatements:false
ALTER TABLE agreements ALTER COLUMN status TYPE VARCHAR(255) USING (status::VARCHAR(255));

-- changeset butt:1781163597499-5 splitStatements:false
ALTER TABLE applications ALTER COLUMN status TYPE VARCHAR(255) USING (status::VARCHAR(255));

-- changeset butt:1781163597499-6 splitStatements:false
ALTER TABLE photos ALTER COLUMN type TYPE VARCHAR(255) USING (type::VARCHAR(255));

