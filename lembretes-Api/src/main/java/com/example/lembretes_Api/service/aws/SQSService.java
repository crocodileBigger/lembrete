package com.example.lembretes_Api.service.aws;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.example.lembretes_Api.entidade.LembretesEvent;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;

@Service
@RequiredArgsConstructor
@Slf4j
public class SQSService {

    private final SqsClient sqsClient;
    private final ObjectMapper objectMapper;

    @Value("${aws.sqs.queue-url}")
    private String queueUrl;

    /**
     * Publica uma mensagem na fila SQS
     * 
     * Fluxo:
     * 1. Recebe um evento de lembrete
     * 2. Serializa para JSON
     * 3. Envia pra fila AWS SQS
     * 4. Retorna imediatamente
     */
    public void enviarLembrete(LembretesEvent evento) {
        try {
            String messageBody = objectMapper.writeValueAsString(evento);
            
            SendMessageRequest request = SendMessageRequest.builder()
                .queueUrl(queueUrl)
                .messageBody(messageBody)
                .delaySeconds(0) // Imediato
                .build();

            SendMessageResponse response = sqsClient.sendMessage(request);
            
            log.info("✅ Mensagem enviada para fila | MessageId: {} | LembretId: {}", 
                response.messageId(), evento.getId());
                
        } catch (Exception e) {
            log.error("❌ Erro ao enviar mensagem para SQS", e);
            throw new RuntimeException("Erro ao enviar lembrete para fila", e);
        }
    }

    /**
     * Versão com delay (para lembretes agendados)
     */
    public void enviarLembreteProgramado(LembretesEvent evento, int delaySeconds) {
        try {
            String messageBody = objectMapper.writeValueAsString(evento);
            
            SendMessageRequest request = SendMessageRequest.builder()
                .queueUrl(queueUrl)
                .messageBody(messageBody)
                .delaySeconds(delaySeconds) // Espera N segundos pra processar
                .build();

            SendMessageResponse response = sqsClient.sendMessage(request);
            
            log.info("⏰ Mensagem agendada | Delay: {}s | MessageId: {}", 
                delaySeconds, response.messageId());
                
        } catch (Exception e) {
            log.error("❌ Erro ao enviar lembrete agendado", e);
            throw new RuntimeException("Erro ao agendar lembrete", e);
        }
    }
}
