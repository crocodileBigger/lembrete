package com.example.lembretes_Api.dto;

import java.util.Set;

public record UsuarioResponseDTO(
    String name,
    String email,
    String password,
    Set<String> roles
) {}
