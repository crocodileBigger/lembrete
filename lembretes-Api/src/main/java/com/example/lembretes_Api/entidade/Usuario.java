package com.example.lembretes_Api.entidade;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "Usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "name")
    private String name;

    @NotBlank
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotBlank
    @Column(name = "password")
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "role")
    private Set<String> role = new HashSet<>();

    public Usuario() {
    }

    public Usuario(String name, String email, String password) {
        this.name = Objects.requireNonNull(name, "Nome é obrigatório");
        this.email = Objects.requireNonNull(email, "Email é obrigatório");
        this.password = Objects.requireNonNull(password, "Senha é obrigatória");
        this.role.add("ROLE_USER");
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Set<String> getRoles() { return role; }
    public void setRole(Set<String> role) { this.role = role; }
}
