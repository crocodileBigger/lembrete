package com.example.lembretes_Api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;
import com.example.lembretes_Api.entidade.Usuario;
import com.example.lembretes_Api.entidade.Email;
import com.example.lembretes_Api.dto.EmailDTOs.EmailrequestDTO;
import com.example.lembretes_Api.repositorio.UsuarioRepository;
import com.example.lembretes_Api.repositorio.EmailRepository;

import java.util.List;
import java.util.Optional;

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

    // Buscar email por ID vindo do DTO
    @Transactional(readOnly = true)
    public Optional<Email> buscarPorId(EmailrequestDTO dto) {
        return emailRepository.findById(dto.id());
    }

    // Buscar todos os emails de um usuário pelo ID do usuário no DTO
    @Transactional(readOnly = true)
    public List<Email> buscarPorUsuarioId(EmailrequestDTO dto) {
        return emailRepository.findByUsuarioId(dto.usuarioId());
    }

    // Criar/Salvar email extraindo dados e ID do usuário via DTO
    @Transactional
    public Email salvar(EmailrequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.usuarioId())
            .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        Email email = new Email();
        email.setDestinatario(dto.destinatario());
        email.setAssunto(dto.assunto());
        email.setMensagem(dto.mensagem());
        email.setAgendamento(dto.agendamento());
        email.setUsuario(usuario);

        return emailRepository.save(email);
    }

    // Atualizar email extraindo os dados e o ID do email via DTO
    @Transactional
    public Email atualizar(EmailrequestDTO dto) {
        Email emailExistente = emailRepository.findById(dto.id())
            .orElseThrow(() -> new IllegalArgumentException("Email não encontrado para o ID: " + dto.id()));

        emailExistente.setDestinatario(dto.destinatario());
        emailExistente.setAssunto(dto.assunto());
        emailExistente.setMensagem(dto.mensagem());
        emailExistente.setAgendamento(dto.agendamento());

        return emailRepository.save(emailExistente);
    }

    // Deletar extraindo o ID via DTO
    @Transactional
    public void deletar(EmailrequestDTO dto) {
        if (!emailRepository.existsById(dto.id())) {
            throw new IllegalArgumentException("Email não encontrado para deleção.");
        }
        emailRepository.deleteById(dto.id());
    }

    // Enviar email acessando o ID via DTO
    @Transactional
    public void enviarEmail(EmailrequestDTO dto) {
        Email email = emailRepository.findById(dto.id())
            .orElseThrow(() -> new IllegalArgumentException("Email não encontrado para envio"));

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email.getDestinatario());
        message.setSubject(email.getAssunto());
        message.setText(email.getMensagem());

        javaMailSender.send(message);
    }

    // Agendar email acessando o ID via DTO
    @Transactional
    public void agendarEmail(EmailrequestDTO dto) {
        Email email = emailRepository.findById(dto.id())
            .orElseThrow(() -> new RuntimeException("Email não encontrado para agendamento"));

        agendamentoService.agendarTarefa(
            email.getAgendamento(),
            () -> enviarEmail(dto)
        );
    }
}
