--liquibase formatted sql
--changeset lamashkevich:1
CREATE TABLE IF NOT EXISTS tax_rates (
  id BIGSERIAL PRIMARY KEY,
  position VARCHAR(100),
  region VARCHAR(100),
  total_rate DECIMAL(10, 2),
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  UNIQUE (position, region)
);