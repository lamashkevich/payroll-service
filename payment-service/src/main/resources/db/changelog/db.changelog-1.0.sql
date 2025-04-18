--liquibase formatted sql
--changeset lamashkevich:1
CREATE TABLE IF NOT EXISTS payments (
  id BIGSERIAL PRIMARY KEY,
  employee_id BIGINT,
  IBAN VARCHAR(34),
  amount DECIMAL(10, 2),
  status VARCHAR(50),
  payment_date DATE,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);