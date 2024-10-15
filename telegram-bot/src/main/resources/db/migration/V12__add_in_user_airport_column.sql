ALTER TABLE flight ADD in_user_airport BOOLEAN DEFAULT FALSE;

UPDATE flight SET in_user_airport = false;