ALTER TABLE flight ADD departure_airport VARCHAR(4);

UPDATE flight SET departure_airport = null;