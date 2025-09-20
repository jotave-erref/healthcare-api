package com.jotave.healthcare.model.patient;

import com.jotave.healthcare.infra.exceptions.BusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // integração do JUnit com mockto
public class PatientServiceTest {

    @InjectMocks //Criando uma instancia real do service e injetando os mocks
    private PatientService service;

    @Mock //dublê do repository
    private PatientRepository repository;

    @Test
    @DisplayName("Deve criar um paciente se os dados estiverem corretos")
    void create_Scenario1(){

        var dataNascimento = LocalDate.of(2020, 01, 02);

        var createDto = new PatientCreateDto(
                "Fulano de Tal",
                "12345678900",
                "1133446655",
                dataNascimento
        );

        when(repository.existsByCpf(createDto.cpf())).thenReturn(false);

        var resultado = service.create(createDto);

        assertThat(resultado).isNotNull();
        assertThat(resultado.nome()).isEqualTo("Fulano de Tal");
        assertThat(resultado.cpf()).isEqualTo("12345678900");
        assertThat(resultado.telefone()).isEqualTo("1133446655");
        assertThat(resultado.dataNascimento()).isEqualTo("2020-01-02");
    }

    @Test
    @DisplayName("Deve lançar exceção quando salvar o paciente com CPF duplicado")
    void create_Scenario02(){
        var dataNascimento = LocalDate.of(2020, 02, 03);

        var createDto = new PatientCreateDto(
                "Fulano de Tal",
                "12345678900",
                "1133446655",
                dataNascimento
        );

        //o existsByCpf retorna true para um cpf já existente
        when(repository.existsByCpf(createDto.cpf())).thenReturn(true);

        //lança exceção ao chamar o metodo create
        var exception = assertThrows(BusinessException.class, () -> {
            service.create(createDto);
        });

        //mensagem de exceção esperada
        assertThat(exception.getMessage()).isEqualTo("CPF já cadastrado");

        //verifica se o metodo save() nunca foi chamado
        verify(repository, never()).save(any());

    }
}
