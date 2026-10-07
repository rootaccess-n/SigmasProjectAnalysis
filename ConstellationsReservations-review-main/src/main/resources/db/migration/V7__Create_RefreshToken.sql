CREATE TABLE refresh_tokens (
                                id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                                jti_hash VARCHAR(1024) NOT NULL,
                                user_id BIGINT NOT NULL,
                                expires_at TIMESTAMP NOT NULL,
                                created_at TIMESTAMP NOT NULL DEFAULT NOW(),
                                valid BOOLEAN NOT NULL DEFAULT TRUE,
                                family_id VARCHAR(1024) NOT NULL,
                                old_refresh_time TIMESTAMP DEFAULT NULL

);