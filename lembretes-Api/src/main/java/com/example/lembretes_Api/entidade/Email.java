package com.example.lembretes_Api.entidade;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn; 
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;
import java.time.LocalDateTime;

@Entity
@Table(name = "Email")
public class Email {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Column(name = "destinatario")
  private String destinatario;
  
  @NotBlank
  @Column(name = "assunto")
  private String assunto;
  
  @NotBlank
  @Column(name = "mensagem")
  private String mensagem;

  @NotNull
  @Column(name = "agendamento")
  private LocalDateTime agendamento;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private Usuario usuario;

  public Email() {
  }

  // Construtor atualizado recebendo o objeto Usuario
    public Email(String destinatario, String assunto, String mensagem, LocalDateTime agendamento, Usuario usuario) {
        this.destinatario = Objects.requireNonNull(destinatario, "Destinatário é obrigatório");
        this.assunto = Objects.requireNonNull(assunto, "Assunto é obrigatório");
        this.mensagem = Objects.requireNonNull(mensagem, "Mensagem é obrigatória");
        this.agendamento = Objects.requireNonNull(agendamento, "Agendamento é obrigatório");
        this.usuario = Objects.requireNonNull(usuario, "Usuário é obrigatório");
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

  // Getters e Setters de agendamento
  public LocalDateTime getAgendamento() { return agendamento; }
  public void setAgendamento(LocalDateTime agendamento) { this.agendamento = agendamento; }

}
