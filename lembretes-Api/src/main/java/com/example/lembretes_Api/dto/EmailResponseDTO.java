package com.example.lembretes_Api.dto;

import java.time.LocalDateTime;

public record EmailResponseDTO(
    Long id,
    String destinatario,
    String assunto,
    String mensagem,
    LocalDateTime agendamento,
    Long usuarioId 
) {}
