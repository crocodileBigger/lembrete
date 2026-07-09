package com.example.lembretes_Api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.lembretes_Api.entidade.Lembrete;
import com.example.lembretes_Api.entidade.LembretesEvent;
import com.example.lembretes_Api.entidade.Usuario;
import com.example.lembretes_Api.dto.CriarLembretDTO;
import com.example.lembretes_Api.service.LembretesService;
import com.example.lembretes_Api.service.UsuarioService;
import com.example.lembretes_Api.service.aws.SQSService;
import java.util.List;

@RestController
@RequestMapping("/api/lembretes")
@RequiredArgsConstructor
@Slf4j
public class LembretesController {

    private final LembretesService lembretesService;
    private final UsuarioService usuarioService;
    private final SQSService sqsService;

    /**
     * POST /api/lembretes
     * 
     * Cria um novo lembrete com email vinculado
     * 
     * Request:
     * {
     *   "titulo": "Reunião importante",
     *   "descricao": "Discutir sobre novo projeto",
     *   "dataLembrete": "2024-07-15T10:00:00"
     * }
     */
    @PostMapping
    public ResponseEntity<Lembrete> criar(
            @RequestBody CriarLembretDTO dto,
            @RequestHeader(value = "userId", required = false) Long usuarioId) {

        // Se não passar userId no header, usar um padrão (você pode ajustar)
        if (usuarioId == null) {
            usuarioId = 1L; // Padrão ou lançar exceção
        }

        log.info("📨 Criando lembrete para usuário: {}", usuarioId);

        // 1. Cria lembrete + email automático
        Lembrete lembrete = lembretesService.criar(dto, usuarioId);

        // 2. Busca usuário para pegar email
        Usuario usuario = usuarioService.pegarPorId(usuarioId)
            .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        // 3. Prepara evento para fila
        LembretesEvent evento = new LembretesEvent(
            lembrete.getId(),
            lembrete.getTitulo(),
            lembrete.getDescricao(),
            usuario.getEmail(),
            lembrete.getDataLembrete(),
            lembrete.getDataCriacao(),
            "PENDENTE"
        );

        // 4. Coloca na fila (assíncrono)
        try {
            sqsService.enviarLembrete(evento);
            log.info("✅ Lembrete enfileirado para processamento");
        } catch (Exception e) {
            log.warn("⚠️ Erro ao enfileirar (mas lembrete foi salvo)", e);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(lembrete);
    }

    /**
     * GET /api/lembretes/{id}
     * 
     * Busca um lembrete específico
     */
    @GetMapping("/{id}")
    public ResponseEntity<Lembrete> buscar(@PathVariable Long id) {
        return lembretesService.buscarPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * GET /api/lembretes
     * 
     * Lista lembretes do usuário autenticado
     */
    @GetMapping
    public ResponseEntity<List<Lembrete>> listar(
            @RequestHeader(value = "userId", required = false) Long usuarioId) {

        if (usuarioId == null) {
            usuarioId = 1L;
        }

        List<Lembrete> lembretes = lembretesService.listarPorUsuario(usuarioId);
        return ResponseEntity.ok(lembretes);
    }

    /**
     * PUT /api/lembretes/{id}
     * 
     * Atualiza um lembrete (e seu email)
     */
    @PutMapping("/{id}")
    public ResponseEntity<Lembrete> atualizar(
            @PathVariable Long id,
            @RequestBody CriarLembretDTO dto) {

        Lembrete lembrete = lembretesService.atualizar(id, dto);
        return ResponseEntity.ok(lembrete);
    }

    /**
     * DELETE /api/lembretes/{id}
     * 
     * Deleta um lembrete
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        lembretesService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

