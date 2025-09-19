package com.jotave.healthcare.model.patient;

import java.time.LocalDate;

public record PatientDetailsDto(
        Long id,
        String nome,
        String cpf,
        String telefone,
        LocalDate dataNascimento
) {
    public PatientDetailsDto(Patient patient){
        this(patient.getId(), patient.getNome(), patient.getCpf(), patient.getTelefone(), patient.getDataNascimento());
    }
}
