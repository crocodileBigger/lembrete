package com.example.lembretes_Api.service;

import org.springframework.transaction.annotation.Transactional;
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

    @Transactional // Mantenha aqui apenas se este método salvar/alterar dados no banco
    public void agendarTarefa(LocalDateTime agendamento, Runnable tarefa) {
        Date dataDisparo = Date.from(agendamento.atZone(ZoneId.systemDefault()).toInstant());
        taskScheduler.schedule(tarefa, dataDisparo);
        System.out.println("Tarefa agendada com sucesso para: " + agendamento);
    }
}
