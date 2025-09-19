package com.jotave.healthcare.model.appointments;

import com.jotave.healthcare.model.doctor.Doctor;
import com.jotave.healthcare.model.patient.Patient;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
@Getter @Setter
@EntityListeners(AuditingEntityListener.class) // Habilita a auditoria do JPA
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private Patient patient;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;
    private LocalDateTime dataHora;
    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    @CreatedDate // Marca este campo para ser preenchido na criação
    @Column(name = "created_at", updatable = false) //mapeia o nome da coluna e a torna imutável
    private LocalDateTime createdAt;
}
