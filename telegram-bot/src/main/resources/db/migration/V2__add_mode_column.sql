ALTER TABLE user_ ADD bot_mode VARCHAR(32);

UPDATE user_ SET bot_mode = 'ALL';