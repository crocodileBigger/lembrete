package com.example.lembretes_Api.config;

import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class JwtSecretConfig {

    @Bean
    public SecretKey secretKey() throws IOException {
        String secret = readSecret("/run/secrets/jwt_secret", "./secrets/jwt_secret.txt");
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    private String readSecret(String containerPathStr, String localPathStr) throws IOException {
        Path containerPath = Paths.get(containerPathStr);
        Path localPath = Paths.get(localPathStr);

        if (Files.exists(containerPath)) {
            return Files.readString(containerPath).trim();
        } else if (Files.exists(localPath)) {
            return Files.readString(localPath).trim();
        }
        
        throw new IllegalStateException("Nenhum arquivo de secret para JWT foi encontrado em: " 
                + containerPathStr + " ou " + localPathStr);
    }
}
