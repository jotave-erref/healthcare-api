package com.jotave.healthcare.infra.exceptions;


//Criada para representar todas as regras de negocio que forem violadas (CPF duplicado, conflito de agendamentos, etc)
public class BusinessException extends RuntimeException{

    public BusinessException(String message){
        super(message);
    }
}
