-- changeset butt:1780570923647-1 splitStatements:false
ALTER TABLE users ADD email VARCHAR(255);

-- changeset butt:1780571150863-1 splitStatements:false
ALTER TABLE users DROP COLUMN email;

