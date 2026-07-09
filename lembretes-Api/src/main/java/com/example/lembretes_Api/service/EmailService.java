package com.example.lembretes_Api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.lembretes_Api.entidade.Email;
import com.example.lembretes_Api.repositorio.EmailRepository;
import com.example.lembretes_Api.repositorio.UsuarioRepository;
import com.example.lembretes_Api.entidade.Usuario;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final UsuarioRepository usuarioRepository;
    private final EmailRepository emailRepository;
    private final JavaMailSender javaMailSender;
    private final AgendamentoService agendamentoService;

    // Buscar um email pelo id
    public Optional<Email> buscarPorId(Long id) {
        return emailRepository.findById(id);
    }

    // Buscar todos os emails de um usuario
    @Transactional
    public List<Email> buscarPorUsuarioId(Long userId) {
        return emailRepository.findByUsuarioId(userId);
    }

    // Salvar um email
    @Transactional
    public Email salvar(Email email, Long userId) {
        Usuario usuario = usuarioRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("Usuário nao encontrado"));
       
        email.setUsuario(usuario);
        return emailRepository.save(email);
    }

    // Atualizar um email
    @Transactional
    public Email atualizar(Long id, Email emailAtualizado) {
        Email emailExistente = emailRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Email não encontrado para o ID: " + id));
        
        emailExistente.setDestinatario(emailAtualizado.getDestinatario());
        emailExistente.setAssunto(emailAtualizado.getAssunto());
        emailExistente.setMensagem(emailAtualizado.getMensagem());
        emailExistente.setAgendamento(emailAtualizado.getAgendamento());

        return emailRepository.save(emailExistente);
    }

    // Deletar um email
    @Transactional
    public void deletar(Long id) {
        emailRepository.deleteById(id);
    }
    
    /**
     * ✅ NOVO: Envia email COM OS DADOS (chamado pelo worker/controller)
     * Recebe dados diretamente sem buscar no BD
     */
    @Transactional
    public void enviarEmail(String destinatario, String assunto, String mensagem) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(destinatario);
            message.setSubject(assunto);
            message.setText(mensagem);
            
            javaMailSender.send(message);
            
            log.info("✉️ Email enviado para: {}", destinatario);
            
        } catch (Exception e) {
            log.error("❌ Erro ao enviar email", e);
            throw new RuntimeException("Erro ao enviar email", e);
        }
    }

    /**
     * LEGACY: Envia email pelo ID (compatibilidade com código antigo)
     */
    @Transactional
    public void enviarEmailPorId(Long emailId) {
       Email email = emailRepository.findById(emailId)
           .orElseThrow(() -> new IllegalArgumentException("Email não encontrado para o ID"));
       
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email.getDestinatario());
        message.setSubject(email.getAssunto());
        message.setText(email.getMensagem());

        javaMailSender.send(message);
        
        // Marca como enviado
        email.setEnviadoEm(LocalDateTime.now());
        emailRepository.save(email);
    }

    // Agendar email existente para envio futuro
    @Transactional
    public void agendarEmail(Long emailId) {
        Email email = emailRepository.findById(emailId)
            .orElseThrow(() -> new RuntimeException("Email nao encontrado para o ID"));
        
        agendamentoService.agendarTarefa(email.getAgendamento(),
            () -> enviarEmailPorId(email.getId())
        );
    }
}

