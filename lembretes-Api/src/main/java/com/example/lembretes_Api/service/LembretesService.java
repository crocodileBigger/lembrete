package com.example.lembretes_Api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.lembretes_Api.entidade.Lembrete;
import com.example.lembretes_Api.entidade.Email;
import com.example.lembretes_Api.entidade.Usuario;
import com.example.lembretes_Api.dto.CriarLembretDTO;
import com.example.lembretes_Api.repositorio.LembretesRepository;
import com.example.lembretes_Api.repositorio.UsuarioRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LembretesService {

    private final LembretesRepository lembretesRepository;
    private final UsuarioRepository usuarioRepository;
    private final EmailService emailService;

    /**
     * Cria um novo lembrete com email vinculado
     * 
     * Fluxo:
     * 1. Busca usuário
     * 2. Cria Email automaticamente
     * 3. Cria Lembrete 
     * 4. Salva ambos no BD
     */
    @Transactional
    public Lembrete criar(CriarLembretDTO dto, Long usuarioId) {
        
        log.info("📝 Criando lembrete para usuário: {}", usuarioId);

        // 1. Busca usuário
        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        // 2. Cria Email automaticamente
        Email email = new Email();
        email.setDestinatario(usuario.getEmail()); // Email do usuário
        email.setAssunto("🔔 Lembrete: " + dto.getTitulo());
        email.setMensagem(criarCorpoEmail(dto)); // Gera corpo automático
        email.setAgendamento(null);

        // Salva email primeiro
        Email emailSalvo = emailService.salvar(email, usuarioId);
        log.info("✉️ Email criado automaticamente | ID: {}", emailSalvo.getId());

        // 3. Cria Lembrete (SEPARADO do Email)
        Lembrete lembrete = new Lembrete();
        lembrete.setTitulo(dto.getTitulo());
        lembrete.setDescricao(dto.getDescricao());
        lembrete.setDataLembrete(dto.getDataLembrete());
        lembrete.setUsuario(usuario);
        lembrete.setStatus("PENDENTE");
        lembrete.setDataCriacao(LocalDateTime.now());

        // 4. Salva lembrete
        Lembrete lembreteSalvo = lembretesRepository.save(lembrete);
        log.info("✅ Lembrete criado | ID: {} | Email destinatário: {}", 
            lembreteSalvo.getId(), emailSalvo.getDestinatario());

        return lembreteSalvo;
    }

    /**
     * Busca lembrete por ID
     */
    public Optional<Lembrete> buscarPorId(Long id) {
        return lembretesRepository.findById(id);
    }

    /**
     * Lista lembretes do usuário
     */
    @Transactional
    public List<Lembrete> listarPorUsuario(Long usuarioId) {
        return lembretesRepository.findByUsuarioId(usuarioId);
    }

    /**
     * Atualiza lembrete
     */
    @Transactional
    public Lembrete atualizar(Long id, CriarLembretDTO dto) {
        
        Lembrete lembrete = lembretesRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Lembrete não encontrado"));

        // Atualiza lembrete
        lembrete.setTitulo(dto.getTitulo());
        lembrete.setDescricao(dto.getDescricao());
        lembrete.setDataLembrete(dto.getDataLembrete());

        return lembretesRepository.save(lembrete);
    }

    /**
     * Deleta lembrete
     */
    @Transactional
    public void deletar(Long id) {
        Lembrete lembrete = lembretesRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Lembrete não encontrado"));

        lembretesRepository.delete(lembrete);
        log.info("🗑️ Lembrete {} deletado", id);
    }

    /**
     * Gera corpo do email automaticamente
     */
    private String criarCorpoEmail(CriarLembretDTO dto) {
        return String.format(
            "Olá,\n\n" +
            "Você tem um lembrete:\n\n" +
            "📌 Título: %s\n" +
            "📝 Descrição: %s\n" +
            "🕐 Data: %s\n\n" +
            "Clique no link para visualizar: http://seu-app.com/lembretes\n\n" +
            "Atenciosamente,\n" +
            "Sistema de Lembretes",
            dto.getTitulo(),
            dto.getDescricao(),
            dto.getDataLembrete()
        );
    }
}

