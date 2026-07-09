package com.example.lembretes_Api.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.lembretes_Api.entidade.Usuario;
import com.example.lembretes_Api.repositorio.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public Optional<Usuario> pegarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    @Transactional
    public List<Usuario> pegarTodos() {
        return usuarioRepository.findAll();
    }

    @Transactional
    public Usuario salvar(Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new DataIntegrityViolationException("email ja cadastrado" + usuario.getEmail());
        }
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario atualizar(Long id, Usuario usuarioAtualizado) {
        // Busca a entidade gerenciada (Managed)
        Usuario usuarioExistente = usuarioRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com o ID: " + id));

        // Atualiza apenas os campos mutáveis, preservando o ID original e dados estruturais
        usuarioExistente.setName(usuarioAtualizado.getName());
        usuarioExistente.setEmail(usuarioAtualizado.getEmail());
        usuarioExistente.setPassword(usuarioAtualizado.getPassword()); // Se aplicável

        // O salvamento é persistido automaticamente pelo contexto de persistência do JPA ao fim da transação
        return usuarioRepository.save(usuarioExistente);
    }

    @Transactional
    public void delete(Long id) {
        // Evita exceções de violação de restrição em runtime verificando a existência previamente
        if (!usuarioRepository.existsById(id)) {
            throw new EntityNotFoundException("Não foi possível deletar. Usuário não encontrado com o ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }
}
