package com.jotave.healthcare.model.patient;

import com.jotave.healthcare.infra.exceptions.BusinessException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    @Autowired
    private PatientRepository repository;

    @Transactional
    public PatientDetailDto create(PatientCreateDto data){
        if(repository.existsByCpf(data.cpf())){
            throw new BusinessException("CPF já cadastrado");
        }
        var patient = new Patient(data);
        repository.save(patient);
        return new PatientDetailDto(patient);
    }
}
