package com.example.lembretes_Api.repositorio;

import com.example.lembretes_Api.entidade.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    // Método necessário para SecurityConfig, AuthService e buscas
    Optional<Usuario> findByEmail(String email);
    
    // Método booleano necessário para a validação no UsuarioService
    boolean existsByEmail(String email);
}
