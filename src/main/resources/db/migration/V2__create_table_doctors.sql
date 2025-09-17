CREATE TABLE doctors (
  id BIGSERIAL PRIMARY KEY,
  nome VARCHAR(200) NOT NULL,
  crm VARCHAR(50) NOT NULL,
  especialidade VARCHAR(100),
  created_at TIMESTAMP DEFAULT now()
);
CREATE UNIQUE INDEX ux_doctors_crm ON doctors(crm);