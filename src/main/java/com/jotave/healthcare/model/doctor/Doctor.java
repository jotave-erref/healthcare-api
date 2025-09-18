package com.jotave.healthcare.model.doctor;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "doctors")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    @NotBlank
    private String crm;

    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;

    public Doctor(DoctorCreateDto data) {
        this.nome = data.nome();
        this.crm = data.crm();
        this.especialidade = data.especialidade();
    }
}
