package com.jotave.healthcare.model.patient;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record PatientCreateDto(
        @NotBlank(message = "Nome é obrigatório")
        String nome,
        @NotBlank(message = "CPF é obrigatório")
        @Pattern( regexp = "\\d{11}", message = "CPF deve conter 11 dígitos")
        String cpf,

        String telefone,

        @NotNull(message = "Data de nascimento é obrigatório")
        @Past(message = "Data de nascimento deve ser no passado")
        LocalDate dataNascimento

) {

}
