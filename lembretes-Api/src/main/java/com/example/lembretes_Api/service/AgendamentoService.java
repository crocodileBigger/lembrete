package com.example.lembretes_Api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
@RequiredArgsConstructor 
public class AgendamentoService {

    private final ThreadPoolTaskScheduler taskScheduler;

    @Transactional
    public void agendarTarefa(LocalDateTime agendamento, Runnable tarefa) {
        Date dataDisparo = Date.from(agendamento.atZone(ZoneId.systemDefault()).toInstant());
        taskScheduler.schedule(tarefa, dataDisparo);
        System.out.println("Tarefa agendada com sucesso para: " + agendamento);
    }
}
