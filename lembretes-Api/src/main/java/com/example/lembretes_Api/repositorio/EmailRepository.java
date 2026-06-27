package com.example.lembretes_Api.repositorio;

import com.example.lembretes_Api.entidade.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List; 

@Repository
public interface EmailRepository extends JpaRepository<Email, Long> {
    
    // Declaração necessária para o Spring gerar o "SELECT * FROM emails WHERE user_id = ?"
    List<Email> findByUserId(Long userId); 
}
