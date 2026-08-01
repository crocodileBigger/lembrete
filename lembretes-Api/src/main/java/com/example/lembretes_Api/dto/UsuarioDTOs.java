package com.example.lembretes_Api.dto;

import java.util.Set;

public class UsuarioDTOs {
  public record UsuarioRequestDTO(long id,String name,String email,String password,
      Set<String> roles) {}
  public record UsuarioResponseDTO(String name,String email,String password,
      Set<String> roles) {}
}
