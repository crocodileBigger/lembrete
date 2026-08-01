package com.example.lembretes_Api.dto;

import java.time.LocalDateTime;

public class EmailDTOs {
    public record EmailrequestDTO(
        long id,
        String destinatario,
        String assunto,
        String mensagem,
        LocalDateTime agendamento,
        long usuarioId // Corrigido de usuarioid para usuarioId
    ) {}

    public record EmailResponseDTO(
        String destinatario,
        String assunto,
        String mensagem,
        LocalDateTime agendamento,
        long usuarioId
    ) {}
}
