package com.jotave.healthcare.model.appointments.validations;

import com.jotave.healthcare.infra.exceptions.BusinessException;
import com.jotave.healthcare.model.appointments.AppointmentScheduleDto;
import com.jotave.healthcare.model.appointments.AppointmentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//Um paciente não pode ter mais de 1 consulta no mesmo dia
@Component
public class ValidatePatientSheduleOnDateTime implements ValidationInterface{

    @Autowired
    private AppointmentsRepository repository;

    @Override
    public void validate(AppointmentScheduleDto data) {
        var dataHora = data.dataHora();
        var horaDoDia = dataHora.withHour(8);
        var diaDaSemana = dataHora.withHour(18);

        var pacienteIdeData = repository.existsByPatientIdAndDataHoraBetween(data.patientId(), horaDoDia, diaDaSemana); //query method que vai avaliar se o pacienteID tem uma consulta marcada para o mesmo dia
        if(pacienteIdeData){
            throw new BusinessException("Paciente já tem uma consulta agendada no mesmo dia");
        }
    }
}
