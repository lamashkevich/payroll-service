INSERT INTO tax_rates (id, position, region, total_rate)
VALUES (1, 'Developer', 'Minsk', 0.10),
        (2, 'Manager', 'Minsk', 0.11),
        (3, 'Developer', 'GRODNO', 0.12),
        (4, 'Manager', 'GRODNO', 0.13);

---- Reset sequence to max id + 1
SELECT setval(pg_get_serial_sequence('tax_rates', 'id'), coalesce(max(id), 0) + 1, false) FROM tax_rates;