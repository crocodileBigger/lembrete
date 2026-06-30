package com.example.lembretes_Api;

import com.example.lembretes_Api.repositorio.EmailRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class EnvioEmailTask {

    private final EmailRepository emailRepository;

    public EnvioEmailTask(EmailRepository emailRepository) {
        this.emailRepository = emailRepository;
    }

    @Transactional(readOnly = true)
    public void executar(Long emailId) {
        emailRepository.findById(emailId).ifPresentOrElse(
            email -> {
                // Aqui você tem acesso ao e-mail e ao Usuário dono dele (Email -> Usuario)
                String emailDoServidor = email.getUsuario().getEmail(); // E-mail de quem está enviando
                String destinatario = email.getDestinatario();
                String conteudo = email.getAssunto();

                System.out.println("LOG: Thread Virtual disparada.");
                System.out.println("Enviando de: " + emailDoServidor + " | Para: " + destinatario);
                
                // Lógica de envio real com JavaMailSender usando os dados acima
            },
            () -> System.err.println("Erro: Tentativa de disparo para E-mail ID " + emailId + " que não existe mais.")
        );
    }
}
