 package com.example.lembretes_Api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.lembretes_Api.entidade.Usuario;
import com.example.lembretes_Api.repositorio.UsuarioRepository;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    //busca um usuario pelo id
    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }

    //busca todos os usuarios
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    //salva um usuario
    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    //atualiza um usuario
    public Usuario update(Long id, Usuario usuario) {
        usuario.setId(id);
        return usuarioRepository.save(usuario);
    }

    //deleta um usuario
    public void deleteById(Long id) {
        usuarioRepository.deleteById(id);
    }
}
