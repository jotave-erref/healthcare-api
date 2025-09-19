package com.jotave.healthcare.model.appointments.validations;

import com.jotave.healthcare.model.appointments.AppointmentDetailsDto;
import com.jotave.healthcare.model.appointments.AppointmentScheduleDto;
import com.jotave.healthcare.model.appointments.AppointmentService;
import jakarta.persistence.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService service;


    @PostMapping
    public ResponseEntity<AppointmentDetailsDto> scheduling(@RequestBody AppointmentScheduleDto data, UriComponentsBuilder uriBuilder){
        var schedule = service.schedule(data);
        var uri = uriBuilder.path("/appointments/{id}").buildAndExpand(schedule.id()).toUri();
        return ResponseEntity.created(uri).body(schedule);
    }
}
