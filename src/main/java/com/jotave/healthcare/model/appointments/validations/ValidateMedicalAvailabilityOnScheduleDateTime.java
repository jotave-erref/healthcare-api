package com.jotave.healthcare.model.appointments.validations;

import com.jotave.healthcare.infra.exceptions.BusinessException;
import com.jotave.healthcare.model.appointments.AppointmentScheduleDto;
import com.jotave.healthcare.model.appointments.AppointmentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//valida se o medico está disponivel na data e hora agendada
@Component
public class ValidateMedicalAvailabilityOnScheduleDateTime implements ValidationInterface{

    @Autowired
    private AppointmentsRepository repository;

    @Override
    public void validate(AppointmentScheduleDto data) {
        var dataHora = repository.existsByDoctorIdAndDataHora(data.doctorId(), data.dataHora());
        if(dataHora){
            throw new BusinessException("Médico indisponível na data e hora agendada!");
        }
    }
}
