package com.example.lembretes_Api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.example.lembretes_Api.dto.UsuarioDTOs.UsuarioRequestDTO;

import com.example.lembretes_Api.entidade.Usuario;
import com.example.lembretes_Api.service.UsuarioService;

import java.util.List;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Pegar um usuario por id
    @GetMapping("/buscar")
    public ResponseEntity<Usuario> getUsuarioById(@RequestBody UsuarioRequestDTO dto) {
        return usuarioService.pegarPorId(dto.id())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build()); // Retorna 404 se não existir
    }

    // Atualizar um usuario
    @PutMapping("/atualizar")
    public ResponseEntity<Usuario> updateUsuario(@RequestBody UsuarioRequestDTO dto) {
        Usuario updatedUsuario = usuarioService.atualizar(dto);
        return ResponseEntity.ok(updatedUsuario);
    }

    // Deletar um usuario
    @DeleteMapping("/deletar")
    public ResponseEntity<Void> deleteUsuario(@RequestBody UsuarioRequestDTO dto) {
        usuarioService.delete(dto);
        return ResponseEntity.noContent().build();
    }
}
