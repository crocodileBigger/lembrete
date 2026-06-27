package com.example.lembretes_Api.repositorio;

import com.example.lembretes_Api.entidade.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List; 

@Repository
public interface EmailRepository extends JpaRepository<Email, Long> {
    
    /*
        O Spring Data JPA permite buscar propriedades de objetos aninhados
        concatenando os nomes. Como na classe Email você tem usuario,
        e dentro de Usuario você tem id, o nome do método deve ser 
        findByUsuarioId:
    */
    List<Email> findByUsuarioId(Long userId);
}
