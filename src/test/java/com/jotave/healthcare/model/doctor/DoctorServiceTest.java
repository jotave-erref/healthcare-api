package com.jotave.healthcare.model.doctor;

import com.jotave.healthcare.infra.exceptions.BusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DoctorServiceTest {

    @Mock
    private DoctorRepository repository;

    @InjectMocks
    private DoctorService service;

    @Test
    @DisplayName("Deve inserir no banco de dados caso os dados estja corretos e válidos")
    public void create_Scenario01(){
        var createDto = new DoctorCreateDto("Fulano", "123456", Especialidade.TRAUMATOLOGIA);

        when(repository.existsByCrm(createDto.crm())).thenReturn(false);

        var resultado = service.create(createDto);

        assertThat(resultado).isNotNull();
        assertThat(resultado.nome()).isEqualTo("Fulano");
        assertThat(resultado.crm()).isEqualTo("123456");
        assertThat(resultado.especilidade()).isEqualTo(Especialidade.TRAUMATOLOGIA);
    }

    @Test
    @DisplayName("Deve lançar exception quando CRM estiver duplicado")
    public void create_Scenario02(){
        var createDto = new DoctorCreateDto("Fulano", "123456", Especialidade.TRAUMATOLOGIA);

        when(repository.existsByCrm(createDto.crm())).thenReturn(true);

        var exception = assertThrows(BusinessException.class, () -> {
            service.create(createDto);
        });

        assertThat(exception.getMessage()).isEqualTo("CRM já cadastrado");

        verify(repository, never()).save(any());
    }
}
