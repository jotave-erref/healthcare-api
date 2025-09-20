// Pacote: com.jotave.healthcare.model.appointment
package com.jotave.healthcare.model.appointment;

import com.jotave.healthcare.infra.exceptions.BusinessException;
import com.jotave.healthcare.model.appointments.Appointment;
import com.jotave.healthcare.model.appointments.AppointmentScheduleDto;
import com.jotave.healthcare.model.appointments.AppointmentService;
import com.jotave.healthcare.model.appointments.AppointmentsRepository;
import com.jotave.healthcare.model.appointments.validations.ValidateMedicalAvailabilityOnScheduleDateTime;
import com.jotave.healthcare.model.appointments.validations.ValidateMinimumAdvance;
import com.jotave.healthcare.model.appointments.validations.ValidatePatientSheduleOnDateTime;
import com.jotave.healthcare.model.appointments.validations.ValidateScheduleOnDateTimeOperation;
import com.jotave.healthcare.model.doctor.Doctor;
import com.jotave.healthcare.model.doctor.DoctorRepository;
import com.jotave.healthcare.model.doctor.Especialidade;
import com.jotave.healthcare.model.patient.Patient;
import com.jotave.healthcare.model.patient.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceTest {

    // A classe que estamos testando
    private AppointmentService appointmentService;

    // Mocks para TODAS as dependências diretas do serviço
    @Mock
    private AppointmentsRepository appointmentsRepository;
    @Mock
    private PatientRepository patientRepository;
    @Mock
    private DoctorRepository doctorRepository;

    // Mocks para CADA uma das validações
    @Mock
    private ValidateScheduleOnDateTimeOperation opValidator;
    @Mock
    private ValidateMinimumAdvance advanceValidator;
    @Mock
    private ValidatePatientSheduleOnDateTime patientValidator;
    @Mock
    private ValidateMedicalAvailabilityOnScheduleDateTime doctorValidator;

    // Objetos de teste reutilizáveis
    private AppointmentScheduleDto scheduleDto;
    private Patient patient;
    private Doctor doctor;

    @BeforeEach
    void setUp() {
        // 1. Criar uma lista contendo os MOCKS das validações
        var validators = List.of(opValidator, advanceValidator, patientValidator, doctorValidator);

        // 2. Criar manualmente a instância do serviço, passando todos os mocks para o construtor.
        // Isso nos dá controle total e resolve o NullPointerException.
        appointmentService = new AppointmentService(appointmentsRepository, patientRepository, doctorRepository, validators);

        // 3. Configurar os dados de teste padrão
        var appointmentTime = LocalDateTime.of(2025, 12, 1, 14, 0); // Data e hora fixas
        this.scheduleDto = new AppointmentScheduleDto(1L, 1L, appointmentTime);
        this.patient = new Patient(1L, "Paciente Teste", "11122233344", "999999999", LocalDate.now());
        this.doctor = new Doctor(1L, "Doutor Teste", "123456SP", Especialidade.DERMATOLOGIA);
    }

    @Test
    @DisplayName("Deveria agendar consulta com sucesso quando todos os dados e validações estão OK")
    void schedule_Success() {
        // Arrange (Organizar)
        // Simular que paciente e médico existem
        when(patientRepository.existsById(scheduleDto.patientId())).thenReturn(true);
        when(doctorRepository.existsById(scheduleDto.doctorId())).thenReturn(true);

        // Simular que as validações (que são mocks) não lançam exceções.
        // doNothing() é o comportamento padrão, mas é bom ser explícito.
        doNothing().when(opValidator).validate(any());
        doNothing().when(advanceValidator).validate(any());
        doNothing().when(patientValidator).validate(any());
        doNothing().when(doctorValidator).validate(any());

        // Simular a busca por referência que ocorre após as validações
        when(patientRepository.getReferenceById(scheduleDto.patientId())).thenReturn(patient);
        when(doctorRepository.getReferenceById(scheduleDto.doctorId())).thenReturn(doctor);

        // Act (Agir)
        var resultDto = appointmentService.schedule(scheduleDto);

        // Assert (Verificar)
        assertThat(resultDto).isNotNull();
        assertThat(resultDto.patientId()).isEqualTo(patient.getId());
        assertThat(resultDto.doctorId()).isEqualTo(doctor.getId());

        // A asserção mais importante: verificar se o agendamento foi de fato salvo.
        verify(appointmentsRepository, times(1)).save(any(Appointment.class));
    }

    @Test
    @DisplayName("Deveria lançar BusinessException quando paciente não existe")
    void schedule_Fail_WhenPatientNotFound() {
        // Arrange (Organizar)
        // Simular que o paciente NÃO existe
        when(patientRepository.existsById(scheduleDto.patientId())).thenReturn(false);

        // Act & Assert (Agir e Verificar)
        var exception = assertThrows(BusinessException.class, () -> {
            appointmentService.schedule(scheduleDto);
        });

        assertThat(exception.getMessage()).isEqualTo("Paciente não encontrado");

        // Garantir que nenhuma validação ou salvamento ocorreu
        verify(doctorValidator, never()).validate(any());
        verify(appointmentsRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deveria lançar BusinessException quando uma das validações falha")
    void schedule_Fail_WhenValidatorThrowsException() {
        // Arrange (Organizar)
        // Simular que paciente e médico existem
        when(patientRepository.existsById(scheduleDto.patientId())).thenReturn(true);
        when(doctorRepository.existsById(scheduleDto.doctorId())).thenReturn(true);

        // CHAVE: Simular que UMA das validações (ex: a de disponibilidade do médico) lança uma exceção.
        var errorMessage = "Médico indisponível na data e hora agendada!";
        doThrow(new BusinessException(errorMessage)).when(doctorValidator).validate(any(AppointmentScheduleDto.class));

        // Act & Assert (Agir e Verificar)
        var exception = assertThrows(BusinessException.class, () -> {
            appointmentService.schedule(scheduleDto);
        });

        assertThat(exception.getMessage()).isEqualTo(errorMessage);

        // Garantir que o processo foi interrompido e NADA foi salvo no banco.
        verify(appointmentsRepository, never()).save(any());
    }
}