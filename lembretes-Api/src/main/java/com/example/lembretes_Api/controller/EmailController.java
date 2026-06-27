package com.example.lembretes_Api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.lembretes_Api.entidade.Email;
import com.example.lembretes_Api.service.EmailService;
import java.util.List;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    // Buscar email pelo ID do próprio e-mail
    @GetMapping("/{id}")
    public ResponseEntity<Email> getEmailById(@PathVariable Long id) {
        return emailService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Buscar todos os e-mails associados a um usuário específico
    // Rota alterada para evitar conflito com a rota acima
    @GetMapping("/usuario/{userId}")
    public ResponseEntity<List<Email>> getEmailsByUserId(@PathVariable Long userId) {
        List<Email> emails = emailService.buscarPorUsuarioId(userId);
        return ResponseEntity.ok(emails);
    }

    @PostMapping("/criar")
    public ResponseEntity<Email> createEmail(@RequestBody Email email) {
        Email savedEmail = emailService.salvar(email);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEmail);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Email> updateEmail(@PathVariable Long id, @RequestBody Email email) {
        Email updatedEmail = emailService.atualizar(id, email);
        return ResponseEntity.ok(updatedEmail);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmail(@PathVariable Long id) {
        emailService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
