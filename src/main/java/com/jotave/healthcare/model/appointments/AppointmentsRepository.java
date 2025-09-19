package com.jotave.healthcare.model.appointments;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface AppointmentsRepository extends JpaRepository<Appointment, Long> {

    boolean existsByDoctorIdAndDataHora(Long doctorId, LocalDateTime dataHora);

    boolean existsByPatientIdAndDataHoraBetween(Long id, LocalDateTime primeiroHorario, LocalDateTime ultimoHorario);
}
