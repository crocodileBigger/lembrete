package com.example.lembretes_Api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CriarLembretDTO {

    private String titulo;
    private String descricao;
    private LocalDateTime dataLembrete;
}
