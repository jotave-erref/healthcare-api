package com.jotave.healthcare.infra.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.Clock;

@Configuration // 1. Anota a classe como uma fonte de definições de beans
public class BeanConfig {

    @Bean // 2. Anota o método para indicar que ele produz um bean a ser gerenciado pelo Spring
    public Clock clock() {
        // 3. Retorna a implementação de Clock que queremos usar na aplicação real:
        // o relógio padrão do sistema, baseado em UTC.
        return Clock.systemUTC();
    }

}
