# MediConnect — Projeto Semestral

O MediConnect simula um hospital inteligente que gerencia consultas, internações, exames, médicos, convênios e notificações. Ele também precisa conversar com sistemas externos de laboratório e operadoras de saúde.

Este repositório representa um **sistema legado em evolução**. O código inicial executa, mas contém decisões incompletas, inconsistências e implementações deliberadamente questionáveis. Durante o semestre, o aluno deverá analisar, justificar e modificar o projeto conforme os conceitos apresentados em aula.

## Escopo inicial
- cadastro simplificado de pacientes e médicos;
- agendamento de consultas;
- solicitação de exames;
- autorização de convênios;
- registro simplificado de internação;
- integração com laboratório externo;
- notificações por e-mail, SMS e WhatsApp;
- priorização de atendimento.

## Execução
Requer Java 17 e Maven.
```bash
mvn compile
java -cp target/classes br.edu.mediconnect.Main
```

## Atividades
Os arquivos `atividades/README_MediConnect_AulaXX.md` orientam a evolução de cada aula. O aluno deve começar o trabalho em sala e registrar as evidências no GitHub.

> Nomes como `Factory`, `Adapter`, `Strategy`, `Observer` e `Facade` não significam que a implementação esteja correta. O aluno deve analisar a necessidade, a forma de uso e as consequências.
