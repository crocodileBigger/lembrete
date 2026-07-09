package com.example.lembretes_Api.entidade;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LembretesEvent implements Serializable {
    
    @JsonProperty("id")
    private Long id;
    
    @JsonProperty("titulo")
    private String titulo;
    
    @JsonProperty("descricao")
    private String descricao;
    
    @JsonProperty("email")
    private String email;
    
    @JsonProperty("data_lembrete")
    private LocalDateTime dataLembrete;
    
    @JsonProperty("data_criacao")
    private LocalDateTime dataCriacao;
    
    @JsonProperty("status")
    private String status;
}
