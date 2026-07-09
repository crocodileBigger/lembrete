package com.example.lembretes_Api.service.aws;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;

@Service
@RequiredArgsConstructor
@Slf4j
public class SNSService {

    private final SnsClient snsClient;
    private final ObjectMapper objectMapper;

    @Value("${aws.sns.topic-arn}")
    private String topicArn;

    /**
     * Publica notificação no SNS
     * 
     * O SNS pode estar configurado para:
     * - Enviar email
     * - Enviar SMS
     * - Chamar Lambda
     * - Disparar outras ações
     */
    public void publicarNotificacao(String assunto, String mensagem) {
        try {
            PublishRequest request = PublishRequest.builder()
                .topicArn(topicArn)
                .subject(assunto)
                .message(mensagem)
                .build();

            PublishResponse response = snsClient.publish(request);
            
            log.info("📢 Notificação publicada | MessageId: {} | Assunto: {}", 
                response.messageId(), assunto);
                
        } catch (Exception e) {
            log.error("❌ Erro ao publicar notificação no SNS", e);
            // NÃO lança exceção aqui, pois é apenas notificação
        }
    }

    public void publicarNotificacaoErro(String titulo, String erro) {
        String mensagem = String.format(
            "ERRO: %s\n\nDetalhes: %s\n\nTempo: %s",
            titulo, erro, java.time.LocalDateTime.now()
        );
        publicarNotificacao("⚠️ ERRO - " + titulo, mensagem);
    }
}
