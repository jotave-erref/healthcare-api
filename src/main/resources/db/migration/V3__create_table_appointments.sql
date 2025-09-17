CREATE TABLE appointments (
  id BIGSERIAL PRIMARY KEY,
  patient_id BIGINT NOT NULL REFERENCES patients(id),
  doctor_id BIGINT NOT NULL REFERENCES doctors(id),
  data_hora TIMESTAMP NOT NULL,
  status VARCHAR(20) NOT NULL DEFAULT 'SCHEDULED',
  created_at TIMESTAMP DEFAULT now()
);
CREATE INDEX idx_appointments_data_hora ON appointments(data_hora);