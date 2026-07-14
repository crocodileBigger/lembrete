Fluxo de dados

[ Cliente (JSON) ]
       │
       ▼  (Requisição HTTP POST)
[ Controller ] ──(Usa o Jackson para converter JSON em...)──► [ EmailRequestDTO ]
                                                                     │
                                                               (Dados Limpos)
                                                                     │
                                                                     ▼
[ EmailRepository ] ◄──(Salva no Banco)── [ Entidade Email ] ◄── [ EmailService ]
       │                                                              │
   (Gera ID)                                                    (Chama c/ ID)
       │                                                              │
       ▼                                                              ▼
[ Banco de Dados ]                                           [ AgendamentoService ]
                                                                      │
                                                              (Virtual Thread)
                                                                      ▼
                                                            [ Envio no Horário X ]
