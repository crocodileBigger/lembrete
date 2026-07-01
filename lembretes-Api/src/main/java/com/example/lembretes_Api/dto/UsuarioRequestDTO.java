package com.example.lembretes_Api.dto;

public record UsuarioRequestDTO(
    String name,
    String email,
    String password
) {}
