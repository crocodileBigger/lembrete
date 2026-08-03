package com.example.lembretes_Api.service;

import com.example.lembretes_Api.dto.EmailDTOs.EmailrequestDTO;
import com.example.lembretes_Api.entidade.Email;
import com.example.lembretes_Api.entidade.Usuario;
import com.example.lembretes_Api.repositorio.EmailRepository;
import com.example.lembretes_Api.repositorio.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final UsuarioRepository usuarioRepository;
    private final EmailRepository emailRepository;
    private final JavaMailSender javaMailSender;
    private final AgendamentoService agendamentoService;

    // Buscar email por ID
    @Transactional(readOnly = true)
    public Optional<Email> buscarPorId(EmailrequestDTO dto) {
        return emailRepository.findById(dto.id());
    }

    // Buscar todos os emails de um usuário
    @Transactional(readOnly = true)
    public List<Email> buscarPorUsuarioId(EmailrequestDTO dto) {
        return emailRepository.findByUsuarioId(dto.usuarioId());
    }

    // Criar/Salvar email
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

    // Atualizar email
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

    // Deletar email
    @Transactional
    public void deletar(EmailrequestDTO dto) {
        if (!emailRepository.existsById(dto.id())) {
            throw new IllegalArgumentException("Email não encontrado para deleção.");
        }
        emailRepository.deleteById(dto.id());
    }

    // Enviar email
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

    // Agendar email
    @Transactional
    public void agendarEmail(EmailrequestDTO dto) {
        Email email = emailRepository.findById(dto.id())
            .orElseThrow(() -> new IllegalArgumentException("Email não encontrado para agendamento"));

        agendamentoService.agendarTarefa(
            email.getAgendamento(),
            () -> enviarEmail(dto)
        );
    }
}
