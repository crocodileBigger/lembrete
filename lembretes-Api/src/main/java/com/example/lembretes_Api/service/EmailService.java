package com.example.lembretes_Api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import com.example.lembretes_Api.entidade.Email;
import com.example.lembretes_Api.repositorio.EmailRepository;
import java.util.List;
import java.util.Optional;

@Service
public class EmailService {

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    public Optional<Email> buscarPorId(Long id) {
        return emailRepository.findById(id);
    }

    public List<Email> buscarPorUsuarioId(Long userId) {
        return emailRepository.findByUserId(userId);
    }

    public Email salvar(Email email) {
        return emailRepository.save(email);
    }

    public Email atualizar(Long id, Email emailAtualizado) {
        emailAtualizado.setId(id);
        return emailRepository.save(emailAtualizado);
    }

    public void deletar(Long id) {
        emailRepository.deleteById(id);
    }

    public void enviarEmail(Long emailId) {
       Email email = emailRepository.findById(emailId).orElseThrow(() -> new IllegalArgumentException("Email não encontrado para o ID"));
       
        //controlar o envio do email
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email.getDestinatario());
        message.setSubject(email.getAssunto());
        message.setText(email.getMensagem());

        //enviar o email via SMTP configurado
        javaMailSender.send(message);
    }
}
