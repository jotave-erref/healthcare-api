package com.jotave.healthcare.model.doctor;

public record DoctorDetailDto(
        Long id,
        String nome,
        String crm,
        Especialidade especilidade
) {
    public DoctorDetailDto(Doctor doctor){
        this(doctor.getId(), doctor.getNome(), doctor.getCrm(), doctor.getEspecialidade());
    }
}
