package com.jotave.healthcare.infra.exceptions;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //Error 404 Not Found
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Void> handleNotFound(){

        //retorna uma resposta 404 vazia padrão para "não encontrado"
        return ResponseEntity.notFound().build();
    }

    //Error 400 bad request (bean validation)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationErrors(MethodArgumentNotValidException ex){

        //pega todos os erros de validação de campo
        var errors = ex.getFieldErrors().stream()
                .map(error -> new ValidationErrorDto(error.getField(), error.getDefaultMessage())).toList();

        //retorna o erro 400 com uma lista de campos inválidos
        return ResponseEntity.badRequest().body(errors);
    }

    //exceção personalizada error 400
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<String> handleBusinessExcption(BusinessException ex){
        // retorna a resposta personalizada na regra de negócio
        return ResponseEntity.badRequest().body(ex.getMessage());
    }


}
