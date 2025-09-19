package com.jotave.healthcare.model.doctor;

public record DoctorDetailsDto(
        Long id,
        String nome,
        String crm,
        Especialidade especilidade
) {
    public DoctorDetailsDto(Doctor doctor){
        this(doctor.getId(), doctor.getNome(), doctor.getCrm(), doctor.getEspecialidade());
    }
}
