package com.example.lembretes_Api.entidade;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.FetchType;

@Entity
@Table(name = "Email")
public class Email {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "desinatario")
  private String desinatario;

  @Column(name = "assunto")
  private String assunto;

  @Column(name = "mensagem")
  private String mensagem;

  @Column(name = "userId")
  private Long userId;

  public Email() {
  }

  public Email(String desinatario, String assunto, String mensagem, Long userId) {
    this.desinatario = desinatario;
    this.assunto = assunto;
    this.mensagem = mensagem;
    this.userId = userId;
  }

  // Getters e Setters
  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }

  // Getters e Setters desinatario
  public String getDesinatario() {
    return desinatario;
  }
  public void setDesinatario(String desinatario) {
    this.desinatario = desinatario;
  }

  // Getters e Setters assunto
  public String getAssunto() {
    return assunto;
  }
  public void setAssunto(String assunto) {
    this.assunto = assunto;
  }

  // Getters e Setters mensagem
  public String getMensagem() {
    return mensagem;
  }
  public void setMensagem(String mensagem) {
    this.mensagem = mensagem;
  }

  // Getters e Setters userId
  public Long getUserId() {
    return userId;
  }
  public void setUserId(Long userId) {
    this.userId = userId;
  }
}
