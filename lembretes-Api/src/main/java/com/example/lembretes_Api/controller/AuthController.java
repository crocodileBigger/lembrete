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

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest dto) {
        var authToken = new UsernamePasswordAuthenticationToken(dto.gmail(), dto.password());
        Authentication auth = authenticationManager.authenticate(authToken);

        String token = tokenService.generateToken(auth);
        return ResponseEntity.ok(Map.of("token", token));
    }
}
