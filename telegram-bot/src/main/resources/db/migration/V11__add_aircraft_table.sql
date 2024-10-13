CREATE TABLE aircraft (
    id UUID PRIMARY KEY,
    family VARCHAR(32) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    user_id UUID NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user_(id) ON DELETE CASCADE
);