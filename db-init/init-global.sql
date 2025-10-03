-- Global DB initiate (postgres-global)

-- Create User table if it does not exist
CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

DO $$
BEGIN
    IF (SELECT COUNT(*) FROM users) = 0 THEN
        INSERT INTO users (name) VALUES
            ('User1'), ('User2'), ('User3'), ('User4'), ('User5'),
            ('User6'), ('User7'), ('User8'), ('User9'), ('User10');
    END IF;
END;
$$;