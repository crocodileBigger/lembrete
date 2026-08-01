package com.example.lembretes_Api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.lembretes_Api.dto.EmailDTOs.EmailrequestDTO;
import com.example.lembretes_Api.entidade.Email;
import com.example.lembretes_Api.service.EmailService;
import java.util.List;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    // Buscar email pelo ID do próprio e-mail
    @GetMapping("/buscar")
    public ResponseEntity<Email> getEmailById(@RequestBody EmailrequestDTO dto) {
        return emailService.buscarPorId(dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Buscar todos os e-mails associados a um usuário específico
    // Rota alterada para evitar conflito com a rota acima
    @GetMapping("/usuario/emails")
    public ResponseEntity<List<Email>> getEmailsByUserId(@RequestBody EmailrequestDTO dto) {
        List<Email> emails = emailService.buscarPorUsuarioId(dto);
        return ResponseEntity.ok(emails);
    }

    @PostMapping("/criar")
    public ResponseEntity<Email> createEmail(@RequestBody EmailrequestDTO dto) {
        Email savedEmail = emailService.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEmail);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<Email> updateEmail(@RequestBody EmailrequestDTO dto) {
        Email updatedEmail = emailService.atualizar(dto);
        return ResponseEntity.ok(updatedEmail);
    }

    @DeleteMapping("/deletar")
    public ResponseEntity<Void> deleteEmail(@RequestBody EmailrequestDTO dto) {
        emailService.deletar(dto);
        return ResponseEntity.noContent().build();
    }

    //enviar email para o destinatario
    @PostMapping("/enviar")
    public ResponseEntity<Void> enviarEmail(@RequestBody EmailrequestDTO dto) {
        emailService.enviarEmail(dto);
        return ResponseEntity.ok().build();
    }

    //agendar email existente para o destinatario basiado no ID
    @PostMapping("/agendar")
    public ResponseEntity<Void> agendarEmail(@RequestBody EmailrequestDTO dto) {
        emailService.agendarEmail(dto);
        return ResponseEntity.ok().build();
    }
}
