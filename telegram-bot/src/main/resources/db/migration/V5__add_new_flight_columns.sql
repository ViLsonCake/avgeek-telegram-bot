ALTER TABLE flight ADD took_off BOOLEAN DEFAULT FALSE;
ALTER TABLE flight ADD landing BOOLEAN DEFAULT FALSE;
ALTER TABLE flight ADD on_ground BOOLEAN DEFAULT FALSE;

UPDATE flight SET took_off = false;
UPDATE flight SET landing = false;
UPDATE flight SET on_ground = false;