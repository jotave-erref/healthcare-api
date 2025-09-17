CREATE TABLE medical_records (
  id BIGSERIAL PRIMARY KEY,
  patient_id BIGINT NOT NULL REFERENCES patients(id),
  appointment_id BIGINT NOT NULL REFERENCES appointments(id),
  diagnostico TEXT,
  observacoes TEXT,
  created_at TIMESTAMP DEFAULT now()
);