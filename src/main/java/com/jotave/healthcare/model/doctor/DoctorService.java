package com.jotave.healthcare.model.doctor;

import com.jotave.healthcare.infra.exceptions.BusinessException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository repository;

    @Transactional
    public DoctorDetailsDto create(DoctorCreateDto data){
        if(repository.existsByCrm(data.crm())){
            throw new BusinessException("CRM já cadastrado");
        }

        var doctor = new Doctor(data);
        repository.save(doctor);
        return new DoctorDetailsDto(doctor);
    }

}
