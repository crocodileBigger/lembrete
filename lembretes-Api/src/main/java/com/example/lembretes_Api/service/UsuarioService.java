package com.example.lembretes_Api.service;

import com.example.lembretes_Api.dto.UsuarioDTOs.UsuarioRequestDTO;
import com.example.lembretes_Api.entidade.Usuario;
import com.example.lembretes_Api.repositorio.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public Optional<Usuario> pegarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Usuario> pegarTodos() {
        return usuarioRepository.findAll();
    }

    @Transactional
    public Usuario salvar(UsuarioRequestDTO dto) {
        if (usuarioRepository.existsByEmail(dto.email())) {
            throw new DataIntegrityViolationException("E-mail já cadastrado: " + dto.email());
        }

        Usuario usuario = new Usuario();
        usuario.setName(dto.name());
        usuario.setEmail(dto.email());
        usuario.setPassword(passwordEncoder.encode(dto.password()));

        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario atualizar(UsuarioRequestDTO dto) {
        Usuario usuarioExistente = usuarioRepository.findById(dto.id())
            .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com o ID: " + dto.id()));

        usuarioExistente.setName(dto.name());
        usuarioExistente.setEmail(dto.email());

        if (dto.password() != null && !dto.password().isBlank()) {
            usuarioExistente.setPassword(passwordEncoder.encode(dto.password()));
        }

        return usuarioRepository.save(usuarioExistente);
    }

    @Transactional
    public void delete(UsuarioRequestDTO dto) {

        long id = dto.id();

        if (!usuarioRepository.existsById(id)) {
            throw new EntityNotFoundException("Não foi possível deletar. Usuário não encontrado com o ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }
}



