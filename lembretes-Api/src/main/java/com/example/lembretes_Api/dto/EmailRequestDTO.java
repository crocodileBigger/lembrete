package com.example.lembretes_Api.dto;

import java.time.LocalDateTime;

public record EmailRequestDTO(
    String destinatario,
    String assunto,
    String mensagem,
    LocalDateTime agendamento,
    Long usuarioId // Passamos apenas o ID do usuário dono do lembrete
) {}
