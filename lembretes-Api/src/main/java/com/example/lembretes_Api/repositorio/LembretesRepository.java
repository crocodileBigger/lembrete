package com.example.lembretes_Api.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.lembretes_Api.entidade.Lembrete;
import java.util.List;

@Repository
public interface LembretesRepository extends JpaRepository<Lembrete, Long> {
    List<Lembrete> findByUsuarioId(Long usuarioId);
}

