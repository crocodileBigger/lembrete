package com.example.lembretes_Api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

@Configuration
public class MailConfig {

    @Bean
    public JavaMailSender javaMailSender() throws IOException {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        // Leitura do e-mail
        String username = readSecret("/run/secrets/gmail_username", "./secrets/gmail_username.txt");
        // Leitura da senha
        String password = readSecret("/run/secrets/gmail_app_password", "./secrets/gmail_app_password.txt");

        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        return mailSender;
    }

    private String readSecret(String containerPathStr, String localPathStr) throws IOException {
        Path containerPath = Paths.get(containerPathStr);
        Path localPath = Paths.get(localPathStr);

        if (Files.exists(containerPath)) {
            return Files.readString(containerPath).trim();
        } else if (Files.exists(localPath)) {
            return Files.readString(localPath).trim();
        }
        return "";
    }
}
