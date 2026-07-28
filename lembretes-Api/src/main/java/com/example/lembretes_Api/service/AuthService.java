package com.example.lembretes_Api.service;

import com.example.lembretes_Api.dto.AuthDTOs.LoginRequest;
import com.example.lembretes_Api.dto.AuthDTOs.TokenResponse;
import com.example.lembretes_Api.repositorio.UsuarioRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;

    public AuthService(AuthenticationManager authenticationManager, 
                       UsuarioRepository usuarioRepository, 
                       JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.usuarioRepository = usuarioRepository;
        this.jwtService = jwtService;
    }

    public TokenResponse login(LoginRequest request) {
        // Autentica credenciais via Spring Security (lança BadCredentialsException se falhar)
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        var usuario = usuarioRepository.findByEmail(request.username()) // Ou findByUsername
            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        // Gera token passando claims extras (ex: perfis/roles)
        String token = jwtService.generateToken(usuario.getEmail(), Map.of("roles", usuario.getRoles()));
        
        return new TokenResponse(token);
    }
}
