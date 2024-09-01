ALTER TABLE user_ ADD units_system VARCHAR(32);

UPDATE user_ SET units_system = 'METRIC';