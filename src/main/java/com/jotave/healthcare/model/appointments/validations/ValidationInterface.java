package com.jotave.healthcare.model.appointments.validations;

import com.jotave.healthcare.model.appointments.AppointmentScheduleDto;

public interface ValidationInterface {

    void validate(AppointmentScheduleDto data);
}
