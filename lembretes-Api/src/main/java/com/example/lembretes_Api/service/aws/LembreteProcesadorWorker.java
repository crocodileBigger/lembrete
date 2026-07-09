package com.example.lembretes_Api.service.aws;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.example.lembretes_Api.entidade.LembretesEvent;
import com.example.lembretes_Api.service.EmailService;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LembreteProcesadorWorker {

    private final SqsClient sqsClient;
    private final ObjectMapper objectMapper;
    private final EmailService emailService;
    private final SNSService snsService;

    @Value("${aws.sqs.queue-url}")
    private String queueUrl;

    /**
     * Executa a cada 5 segundos
     * Consome mensagens da fila SQS
     */
    @Scheduled(fixedRate = 5000) // 5 segundos
    public void processarFilaPendente() {
        log.debug("🔄 Verificando fila SQS...");
        
        try {
            // 1️⃣ Recebe mensagens da fila (máximo 10)
            ReceiveMessageRequest request = ReceiveMessageRequest.builder()
                .queueUrl(queueUrl)
                .maxNumberOfMessages(10)
                .waitTimeSeconds(20) // Long polling
                .visibilityTimeout(300) // 5 minutos
                .build();

            List<Message> messages = sqsClient.receiveMessage(request)
                .messages();

            if (messages.isEmpty()) {
                log.debug("Fila vazia");
                return;
            }

            log.info("📥 Processando {} mensagens da fila", messages.size());

            // 2️⃣ Processa cada mensagem
            messages.forEach(this::processarMensagem);

        } catch (Exception e) {
            log.error("❌ Erro ao processar fila", e);
            snsService.publicarNotificacaoErro("Erro na fila SQS", e.getMessage());
        }
    }

    /**
     * Processa uma mensagem individual
     */
    private void processarMensagem(Message message) {
        try {
            log.info("⚙️ Processando mensagem: {}", message.messageId());

            // 1. Deserializa JSON → Objeto Java
            LembretesEvent evento = objectMapper.readValue(
                message.body(),
                LembretesEvent.class
            );

            log.info("📨 Lembrete ID: {} | Email: {} | Título: {}",
                evento.getId(), evento.getEmail(), evento.getTitulo());

            // 2. Executa ações pesadas
            enviarEmail(evento);
            publicarNotificacao(evento);
            
            // 3. Se tudo OK, remove da fila
            removerDaFila(message);

            log.info("✅ Lembrete {} processado com sucesso", evento.getId());

        } catch (Exception e) {
            log.error("❌ Erro ao processar mensagem: {}", message.messageId(), e);
            
            // A mensagem fica na fila e será retentada
            // Após 3 tentativas, vai para Dead Letter Queue
            // (se configurado)
        }
    }

    /**
     * Envia o email do lembrete
     */
    private void enviarEmail(LembretesEvent evento) {
        try {
            String assunto = "🔔 Lembrete: " + evento.getTitulo();
            String corpo = String.format(
                "Olá,\n\n" +
                "Você tem um lembrete:\n\n" +
                "📌 Título: %s\n" +
                "📝 Descrição: %s\n" +
                "🕐 Data: %s\n\n" +
                "Atenciosamente,\n" +
                "Sistema de Lembretes",
                evento.getTitulo(),
                evento.getDescricao(),
                evento.getDataLembrete()
            );

            emailService.enviarEmail(evento.getEmail(), assunto, corpo);
            log.info("✉️ Email enviado para: {}", evento.getEmail());

        } catch (Exception e) {
            log.error("❌ Erro ao enviar email", e);
            throw e; // Relança para tentar de novo
        }
    }

    /**
     * Publica notificação no SNS
     */
    private void publicarNotificacao(LembretesEvent evento) {
        try {
            String mensagem = String.format(
                "Lembrete processado:\n" +
                "ID: %d\n" +
                "Título: %s\n" +
                "Email: %s",
                evento.getId(), evento.getTitulo(), evento.getEmail()
            );

            snsService.publicarNotificacao("Lembrete Processado", mensagem);

        } catch (Exception e) {
            log.warn("⚠️ Erro ao publicar notificação (não é crítico)", e);
        }
    }

    /**
     * Remove a mensagem da fila (só se processou com sucesso)
     */
    private void removerDaFila(Message message) {
        try {
            DeleteMessageRequest deleteRequest = DeleteMessageRequest.builder()
                .queueUrl(queueUrl)
                .receiptHandle(message.receiptHandle())
                .build();

            sqsClient.deleteMessage(deleteRequest);
            log.debug("🗑️ Mensagem removida da fila");

        } catch (Exception e) {
            log.error("❌ Erro ao remover mensagem da fila", e);
            throw e;
        }
    }
}
