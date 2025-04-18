INSERT INTO payments (id, employee_id, IBAN, amount, status, payment_date)
VALUES (1, 1, 'GB33BUKB20201555555555', 100, 'COMPLETE', '2025-05-05'),
        (2, 2, 'GB33BUKB20201555555555', 200, 'COMPLETE', '2025-05-05'),
        (3, 1, 'GB33BUKB20201555555555', 300, 'FAILED', '2025-05-06'),
        (4, 2, 'GB33BUKB20201555555555', 400, 'FAILED', '2025-05-06');

---- Reset sequence to max id + 1
SELECT setval(pg_get_serial_sequence('payments', 'id'), coalesce(max(id), 0) + 1, false) FROM payments;