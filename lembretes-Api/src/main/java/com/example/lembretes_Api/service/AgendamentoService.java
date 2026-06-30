package com.example.lembretes_Api.service;

import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class AgendamentoService {

    private final ThreadPoolTaskScheduler taskScheduler;

    public AgendamentoService(ThreadPoolTaskScheduler taskScheduler) {
        this.taskScheduler = taskScheduler;
    }
    public void agendarTarefa(LocalDateTime agendamento, Runnable tarefa) {
        // Converte LocalDateTime para o tipo java.util.Date exigido pelo scheduler
        Date dataDisparo = Date.from(agendamento.atZone(ZoneId.systemDefault()).toInstant());

        // Agenda a execução exata para a data configurada
        taskScheduler.schedule(tarefa, dataDisparo);
        
        System.out.println("Tarefa agendada com sucesso para: " + agendamento);
    }
}
