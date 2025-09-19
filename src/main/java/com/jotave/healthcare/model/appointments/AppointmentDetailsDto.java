package com.jotave.healthcare.model.appointments;

import java.time.LocalDateTime;

public record AppointmentDetailsDto(Long id,
                                    Long patientId,
                                    String patientName,
                                    Long doctorId,
                                    String doctorName,
                                    LocalDateTime dataHora,
                                    LocalDateTime createdAt // Novo campo
) {
    public AppointmentDetailsDto(Appointment appointment) {
        this(appointment.getId(),
                appointment.getPatient().getId(),
                appointment.getPatient().getNome(),
                appointment.getDoctor().getId(),
                appointment.getDoctor().getNome(),
                appointment.getDataHora(),
                appointment.getCreatedAt()); // Mapeando o novo campo
    }
}
