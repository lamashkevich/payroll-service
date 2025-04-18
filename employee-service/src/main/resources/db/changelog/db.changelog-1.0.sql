--liquibase formatted sql
--changeset lamashkevich:1
CREATE TABLE IF NOT EXISTS employees (
  id BIGSERIAL PRIMARY KEY,
  first_name VARCHAR(100),
  last_name VARCHAR(100),
  position VARCHAR(100),
  region VARCHAR(100),
  employment_type VARCHAR(50),
  salary DECIMAL(10, 2),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

--changeset lamashkevich:2
ALTER TABLE employees
ADD COLUMN IBAN VARCHAR(34);