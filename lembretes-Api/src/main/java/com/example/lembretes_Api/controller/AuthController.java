package com.example.lembretes_Api.controller;

import com.example.lembretes_Api.dto.AuthDTOs.LoginRequest;
import com.example.lembretes_Api.dto.AuthDTOs.TokenResponse;
import com.example.lembretes_Api.service.AuthService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import com.example.lembretes_Api.service.TokenService; // Ajuste se o pacote for diferente
import java.util.Map;
import com.example.lembretes_Api.dto.UsuarioDTOs.UsuarioRequestDTO;
import com.example.lembretes_Api.entidade.Usuario;
import com.example.lembretes_Api.service.UsuarioService;
import java.util.List;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest dto) {
        var authToken = new UsernamePasswordAuthenticationToken(dto.gmail(), dto.password());
        Authentication auth = authenticationManager.authenticate(authToken);

        String token = tokenService.generateToken(auth);
        return ResponseEntity.ok(Map.of("token", token));
    }

    // Criar um usuario
    @PostMapping("/usuarioCriar")
    public ResponseEntity<Usuario> createUsuario(@RequestBody UsuarioRequestDTO dto) {
        Usuario savedUsuario = usuarioService.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUsuario);
    }
    
    // usado para teste (não tera no projeto final)
    // Pegar todos os usuarios
    @GetMapping("/usuarioAll")
    public ResponseEntity<List<Usuario>> getAllUsuarios() {
        return ResponseEntity.ok(usuarioService.pegarTodos());
    }
}
