# Aula 04 — Refatoração, separação de responsabilidades e testes

## Problema observado

Durante a análise do projeto MediConnect, foi identificado que a classe `HospitalApplicationService` possuía responsabilidades diferentes dentro do sistema.

Além de coordenar os principais processos hospitalares, como consultas e exames, a classe também conhecia detalhes relacionados às notificações enviadas aos pacientes.

No fluxo de consulta, a própria classe determinava o canal de comunicação e construía a mensagem enviada ao paciente.

No fluxo de exames, também havia responsabilidade relacionada à construção e envio das notificações.

Essa concentração de responsabilidades deixava a classe com baixa coesão e aumentava o impacto de futuras alterações.

## Responsabilidades antes da refatoração

### HospitalApplicationService

Antes da alteração, a classe precisava conhecer:

- o processo de consultas;
- o processo de exames;
- o processo de internações;
- o canal de notificação;
- o destino da notificação;
- o formato das mensagens;
- a publicação de eventos.

### NotificationService

A `NotificationService` possuía operações de envio de notificações, mas parte da lógica de preparação dessas notificações ainda permanecia no serviço principal.

## Alteração realizada

Foram adicionados à `NotificationService` métodos específicos para os fluxos utilizados pela aplicação:

- `notifyAppointmentScheduled()`;
- `notifyExamStatus()`.

A `HospitalApplicationService` passou a solicitar a notificação adequada sem precisar construir diretamente as mensagens utilizadas nesses processos.

## Responsabilidades depois da refatoração

### HospitalApplicationService

Após a refatoração, permanece responsável principalmente por:

- coordenar os processos hospitalares;
- verificar os dados necessários para cada operação;
- atualizar o estado das entidades;
- utilizar os repositórios;
- acionar integrações e serviços necessários;
- publicar eventos.

### NotificationService

Passou a concentrar:

- definição das notificações;
- construção das mensagens;
- escolha do envio correspondente;
- comunicação com o paciente.

## Princípio aplicado

A mudança está relacionada principalmente ao **Single Responsibility Principle (SRP)**.

O objetivo não foi dividir todo o sistema em várias classes novas, mas separar uma responsabilidade que estava claramente misturada com a coordenação dos processos hospitalares.

Dessa forma, alterações relacionadas às mensagens e notificações ficam mais concentradas na `NotificationService`.

## Teste automatizado

Para verificar que o comportamento principal continuou funcionando após a refatoração, foi criado um teste automatizado utilizando **JUnit 5**.

Arquivo:

`src/test/java/br/edu/mediconnect/service/HospitalApplicationServiceTest.java`

Foram criados dois cenários de teste.

### Agendamento válido

O teste `scheduleShouldRegisterAppointmentAsScheduled()` verifica se:

- o paciente existe;
- a operação de agendamento retorna `true`;
- a consulta recebe o status `SCHEDULED`;
- a consulta é armazenada no repositório.

### Paciente inexistente

O teste `scheduleShouldFailWhenPatientDoesNotExist()` verifica se:

- uma consulta para um paciente inexistente não é agendada;
- a operação retorna `false`;
- o status da consulta permanece `CREATED`;
- a consulta não é armazenada no repositório.

## Configuração dos testes

O projeto foi configurado para utilizar **JUnit 5** através do Maven.

A dependência `junit-jupiter` foi adicionada ao arquivo `pom.xml` com escopo de teste.

Também foi configurado o `maven-surefire-plugin` para execução dos testes automatizados.

## Validação

A compilação e os testes foram executados através do comando:

```bash
mvn clean test