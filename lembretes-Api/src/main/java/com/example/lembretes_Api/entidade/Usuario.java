package com.example.lembretes_Api.entidade;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Table(name = "Usuario")
public class Usuario {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name")
  private String name;

  // email tem que ser unico na tabela
  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @Column(name = "password")
  private String password;

  public Usuario() {
  }

  public Usuario(String name, String email, String password) {
    this.name = name;
    this.email = email;
    this.password = password;
  }

  //Getters and Setters id
  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }

  // Getters and Setters name
  public String getName() { return name; }
  public void setName(String name) { this.name = name; }

  // Getters and Setters email
  public String getEmail() { return email; }
  public void setEmail(String email) { this.email = email; }

  // Getters and Setters password
  public String getPassword() { return password; }
  public void setPassword(String password) { this.password = password; }
}
