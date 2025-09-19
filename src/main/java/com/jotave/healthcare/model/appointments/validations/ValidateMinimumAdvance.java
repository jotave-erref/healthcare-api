package com.jotave.healthcare.model.appointments.validations;


import com.jotave.healthcare.infra.exceptions.BusinessException;
import com.jotave.healthcare.model.appointments.AppointmentScheduleDto;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

//Valida a antecedencia minima de 1 hora para marcação da consulta
@Component
public class ValidateMinimumAdvance implements ValidationInterface{


    @Override
    public void validate(AppointmentScheduleDto data) {
        var dataHora = data.dataHora();
        var horaAtual = LocalDateTime.now();
        var antecedenciaMinima = Duration.between(horaAtual, dataHora).toMinutes();
            if(antecedenciaMinima < 60){
            throw new BusinessException("A Consulta deve ser agendada com antecedência mínima de 1 hora");
        }
    }
}
