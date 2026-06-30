package com.example.lembretes_Api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

import com.example.lembretes_Api.service.AgendamentoService;
import com.example.lembretes_Api.repositorio.EmailRepository;
import com.example.lembretes_Api.repositorio.UsuarioRepository;
import com.example.lembretes_Api.entidade.Usuario;
import com.example.lembretes_Api.entidade.Email;

@Service
public class EmailService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private AgendamentoService agendamentoService;

    // Buscar um email pelo id
    public Optional<Email> buscarPorId(Long id) {
        return emailRepository.findById(id);
    }

    // Buscar todos os emails de um usuario
    public List<Email> buscarPorUsuarioId(Long userId) {
        return emailRepository.findByUsuarioId(userId);
    }

    // Salvar um email
    public Email salvar(Email email, Long userId) {
        Usuario usuario = usuarioRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("Usuário nao encontrado"));
       
        email.setUsuario(usuario);
        return emailRepository.save(email);
    }

    // CORREÇÃO 1: Usando o nome correto da variável (emailAtualizado) de forma segura
    public Email atualizar(Long id, Email emailAtualizado) {
        Email emailExistente = emailRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Email não encontrado para o ID: " + id));
        
        emailExistente.setDestinatario(emailAtualizado.getDestinatario());
        emailExistente.setAssunto(emailAtualizado.getAssunto());
        emailExistente.setMensagem(emailAtualizado.getMensagem());
        emailExistente.setAgendamento(emailAtualizado.getAgendamento());

        return emailRepository.save(emailExistente);
    }

    // CORREÇÃO 2: Retornando ao seu método original que não gera erros na linha 66
    public void deletar(Long id) {
        emailRepository.deleteById(id);
    }
    
    // Enviar o email usando o JavaMailSender
    public void enviarEmail(Long emailId) {
       Email email = emailRepository.findById(emailId)
           .orElseThrow(() -> new IllegalArgumentException("Email não encontrado para o ID"));
       
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email.getDestinatario());
        message.setSubject(email.getAssunto());
        message.setText(email.getMensagem());

        javaMailSender.send(message);
    }

    // CORREÇÃO 3: Verifique se o método 'getAgendamento()' da sua entidade Email retorna um LocalDateTime.
    public void agendarEmail(Long emailId) {
        Email email = emailRepository.findById(emailId)
            .orElseThrow(() -> new RuntimeException("Email nao encontrado para o ID"));
        
        // Garanta que o segundo parâmetro seja o LocalDateTime esperado pelo AgendamentoService
        agendamentoService.agendarTarefa(email.getAgendamento(),
            () -> enviarEmail(email.getId())
        );
    }
}
