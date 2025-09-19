package com.jotave.healthcare.model.appointments;

import com.jotave.healthcare.infra.exceptions.BusinessException;
import com.jotave.healthcare.model.appointments.validations.ValidationInterface;
import com.jotave.healthcare.model.doctor.DoctorRepository;
import com.jotave.healthcare.model.patient.PatientRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentsRepository repository;

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private List<ValidationInterface> validations = new ArrayList<>();


    @Transactional
    public AppointmentDetailsDto schedule(AppointmentScheduleDto data){

        if(!patientRepository.existsById(data.patientId())){
            throw new BusinessException("Paciente não encontrado");
        }

        if(!doctorRepository.existsById(data.doctorId())){
            throw new BusinessException("Médico não encontrado");
        }

        validations.forEach(v -> v.validate(data));

        var patient = patientRepository.getReferenceById(data.patientId());
        var doctor = doctorRepository.getReferenceById(data.doctorId());

        var appointment = new Appointment(null, patient, doctor, data.dataHora(), AppointmentStatus.AGENDADO, null);

        repository.save(appointment);
        return new AppointmentDetailsDto(appointment);
    }
}
