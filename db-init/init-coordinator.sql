-- Create User table if it does not exist
CREATE TABLE IF NOT EXISTS users (
                                     id BIGSERIAL PRIMARY KEY,
                                     name VARCHAR(100) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
    );

-- Create Transaction table if it does not exist
CREATE TABLE IF NOT EXISTS transactions (
                                            id BIGSERIAL PRIMARY KEY,
                                            user_id BIGINT REFERENCES users(id),
    amount DECIMAL(10,2) NOT NULL,
    status VARCHAR(50) NOT NULL,
    type VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
    );

-- Register tables as distributed tables with 'id' (users) and 'user_id' (transactions) as sharding keys
SELECT create_distributed_table('users', 'id');
SELECT create_distributed_table('transactions', 'user_id');

-- Insert 10 users (data will be automatically sharded across worker nodes)
DO $$
BEGIN
    IF (SELECT COUNT(*) FROM users) = 0 THEN
        INSERT INTO users (name) VALUES
            ('User1'), ('User2'), ('User3'), ('User4'), ('User5'),
            ('User6'), ('User7'), ('User8'), ('User9'), ('User10');
END IF;
END
$$;