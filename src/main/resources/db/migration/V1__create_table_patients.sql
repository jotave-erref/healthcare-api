CREATE TABLE patients (
  id BIGSERIAL PRIMARY KEY,
  nome VARCHAR(200) NOT NULL,
  cpf VARCHAR(14) NOT NULL,
  telefone VARCHAR(20),
  data_nascimento DATE,
  created_at TIMESTAMP DEFAULT now()
);

CREATE UNIQUE INDEX ux_patients_cpf ON patients(cpf);