package com.example.lembretes_Api.entidade;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn; 

@Entity
@Table(name = "Email")
public class Email {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "destinatario")
  private String destinatario;
  
  @Column(name = "assunto")
  private String assunto;
  
  @Column(name = "mensagem")
  private String mensagem;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private Usuario usuario;

  public Email() {
  }

  // Construtor atualizado recebendo o objeto Usuario
  public Email(String destinatario, String assunto, String mensagem, Usuario usuario) {
    this.destinatario = destinatario;
    this.assunto = assunto;
    this.mensagem = mensagem;
    this.usuario = usuario;
  }

  // Getter e Setter para o ID (O Maven reclamou da falta do setId no service)
  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }

  // Getters e Setters de destinatario
  public String getDestinatario() { return destinatario; }
  public void setDestinatario(String destinatario) { this.destinatario = destinatario; }

  // Getters e Setters de assunto
  public String getAssunto() { return assunto; }
  public void setAssunto(String assunto) { this.assunto = assunto; }

  // Getters e Setters de mensagem
  public String getMensagem() { return mensagem; }
  public void setMensagem(String mensagem) { this.mensagem = mensagem; }

  // Getters e Setters de usuario
  public Usuario getUsuario() { return usuario; }
  public void setUsuario(Usuario usuario) { this.usuario = usuario; }
}
