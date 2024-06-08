CREATE TABLE user_ (
    id UUID PRIMARY KEY,
    username VARCHAR(32) NOT NULL UNIQUE,
    chat_id BIGINT UNIQUE,
    airport VARCHAR(4),
    state VARCHAR(32)
);

CREATE TABLE flight (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    flight_id VARCHAR(32) NOT NULL,
    distance INTEGER,
    active BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user_(id) ON DELETE CASCADE
);