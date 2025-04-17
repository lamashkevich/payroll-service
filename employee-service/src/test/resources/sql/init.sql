INSERT INTO employees (id, first_name, last_name, position, region, salary, employment_type)
VALUES (1, 'FirstName 1', 'LastName 1', 'Developer', 'Minsk', 1000, 'FULL_TIME'),
        (2, 'FirstName 2', 'LastName 2', 'Developer', 'Minsk', 1000, 'FULL_TIME'),
        (3, 'FirstName 3', 'LastName 3', 'Developer', 'GRODNO', 1000, 'FULL_TIME'),
        (4, 'FirstName 4', 'LastName 4', 'Manager', 'GRODNO', 1000, 'PART_TIME'),
        (5, 'FirstName 5', 'LastName 5', 'Manager', 'Minsk', 1000, 'PART_TIME');

---- Reset sequence to max id + 1
SELECT setval(pg_get_serial_sequence('employees', 'id'), coalesce(max(id), 0) + 1, false) FROM employees;

UPDATE employees SET IBAN = 'GB33BUKB20201555555555';
