package com.example.lembretes_Api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


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

    //buscar um email pelo id
    public Optional<Email> buscarPorId(Long id) {
        return emailRepository.findById(id);
    }

    //buscar todos os emails de um usuario
    public List<Email> buscarPorUsuarioId(Long userId) {
        return emailRepository.findByUsuarioId(userId);
    }

    //salvar um email
    public Email salvar(Email email, Long userId) {
        Usuario usuario = usuarioRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("Usuário nao encontrado"));
       
        //associar o email ao usuário
        email.setUsuario(usuario);
        return emailRepository.save(email);
    }

    //atualizar um email
    public Email atualizar(Long id, Email emailAtualizado) {
        emailAtualizado.setId(id);
        return emailRepository.save(emailAtualizado);
    }

    //deletar um email
    public void deletar(Long id) {
        emailRepository.deleteById(id);
    }
    
    //metodo para enviar o email usando o JavaMailSender
    public void enviarEmail(Long emailId) {
       Email email = emailRepository.findById(emailId)
           .orElseThrow(() -> new IllegalArgumentException("Email não encontrado para o ID"));
       
        //controlar o envio do email
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email.getDestinatario());
        message.setSubject(email.getAssunto());
        message.setText(email.getMensagem());

        //enviar o email via SMTP configurado
        javaMailSender.send(message);
    }
}
