ALTER TABLE flight ADD flying_near BOOLEAN DEFAULT FALSE;

UPDATE flight SET flying_near = false;