package com.jotave.healthcare.model.doctor;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    @Autowired
    private DoctorService service;

    @PostMapping
    public ResponseEntity<DoctorDetailDto> createDoctor(@Valid @RequestBody DoctorCreateDto data, UriComponentsBuilder uriBuilder){
        var doctor = service.create(data);
        var uri = uriBuilder.path("/doctors/{id}").buildAndExpand(doctor.id()).toUri();
        return ResponseEntity.created(uri).body(doctor);
    }

}
