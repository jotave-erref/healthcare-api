package com.jotave.healthcare.model.patient;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "patients", uniqueConstraints = {@UniqueConstraint(columnNames = "cpf")})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    private String nome;

    @NotBlank
    private String cpf;

    private String telefone;
    private LocalDate dataNascimento;

    public Patient(PatientCreateDto dto){
        this.nome = dto.nome();
        this.cpf = dto.cpf();
        this.telefone = dto.telefone();
        this.dataNascimento = dto.dataNascimento();
    }
}
