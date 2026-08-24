# Aula 04 — Refatoração e aplicação de SOLID

## Problema observado

A `HospitalApplicationService` possuía responsabilidades relacionadas à coordenação dos processos hospitalares e também aos detalhes das notificações.

No fluxo de consulta, a classe determinava diretamente o uso de e-mail e montava a mensagem enviada ao paciente.

No fluxo de exame, a classe determinava o uso de WhatsApp e também construía a mensagem.

## Alteração realizada

Foram adicionados à `NotificationService` os métodos:

* `notifyAppointmentScheduled()`;
* `notifyExamStatus()`.

A `HospitalApplicationService` passou apenas a solicitar a notificação adequada.

## Antes

A `HospitalApplicationService` precisava conhecer:

* o processo de consulta;
* o processo de exames;
* internações;
* canal de notificação;
* destino da notificação;
* formato da mensagem;
* publicação de eventos.

## Depois

A `HospitalApplicationService` continua coordenando os processos hospitalares, porém os detalhes de comunicação ficam concentrados na `NotificationService`.

## Princípio aplicado

A mudança está relacionada principalmente ao **Single Responsibility Principle (SRP)**.

A separação aumenta a coesão, pois comportamentos relacionados a notificações passam a ficar concentrados no serviço responsável por notificações.

## Critério de aceitação

A refatoração é considerada válida quando:

* o projeto continua compilando;
* consultas continuam sendo marcadas como `SCHEDULED`;
* exames mantêm seus estados anteriores;
* notificações continuam sendo executadas;
* a `HospitalApplicationService` não monta diretamente as mensagens de consulta e exame.
