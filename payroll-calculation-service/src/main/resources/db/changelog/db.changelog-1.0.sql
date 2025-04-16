--liquibase formatted sql
--changeset lamashkevich:1
CREATE TABLE IF NOT EXISTS payrolls (
  id BIGSERIAL PRIMARY KEY,
  employee_id BIGINT,
  base_salary DECIMAL(10, 2),
  total_bonuses DECIMAL(10, 2),
  total_deductions DECIMAL(10, 2),
  tax_amount DECIMAL(10, 2),
  final_salary DECIMAL(10, 2),
  payment_type VARCHAR(50),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);