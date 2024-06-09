ALTER TABLE user_ ADD email VARCHAR(64);
ALTER TABLE user_ ADD email_verified BOOLEAN DEFAULT FALSE;
ALTER TABLE user_ ADD email_code INTEGER DEFAULT 0;

UPDATE user_ SET email = null;
UPDATE user_ SET email_verified = false;
UPDATE user_ SET email_code = 0;