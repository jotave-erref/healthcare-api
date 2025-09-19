package com.jotave.healthcare.model.appointments.validations;

import com.jotave.healthcare.infra.exceptions.BusinessException;
import com.jotave.healthcare.model.appointments.AppointmentScheduleDto;
import com.jotave.healthcare.model.appointments.AppointmentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

//Valida se o agendamento foi feito dentro do horário de funcionamento da clínica
//Horario de funcionamento da clínica é de segunda a sábado das 8:00 às 18:00
@Component
public class ValidateScheduleOnDateTimeOperation implements ValidationInterface {


    @Override
    public void validate(AppointmentScheduleDto data) {
        var dataHoraDaConsulta = data.dataHora();
        var horarioDeAbertura = dataHoraDaConsulta.getHour() < 8;
        var horarioDeFechamento = dataHoraDaConsulta.getHour() > 17;

        var horarioDoDia = dataHoraDaConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);

        if(horarioDeAbertura || horarioDeFechamento || horarioDoDia){ //SE o agendamento foi feito antes das 8:00 ou depois das 17:00 (1hr antes do fechamento) lançará exceção personalizada
            throw new BusinessException("Agendamento de consultas disponível entre 8:00 horas às 18:00 horas de segunda a sábado");
        }


    }
}
