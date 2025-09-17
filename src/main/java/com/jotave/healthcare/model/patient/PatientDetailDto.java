package com.jotave.healthcare.model.patient;

import java.time.LocalDate;

public record PatientDetailDto(
        Long id,
        String nome,
        String cpf,
        String telefone,
        LocalDate dataNascimento
) {
    public PatientDetailDto(Patient patient){
        this(patient.getId(), patient.getNome(), patient.getCpf(), patient.getTelefone(), patient.getDataNascimento());
    }
}
