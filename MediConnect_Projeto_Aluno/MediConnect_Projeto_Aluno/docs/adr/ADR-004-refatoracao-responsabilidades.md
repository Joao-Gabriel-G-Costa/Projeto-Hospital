# ADR-004 — Refatoração de responsabilidades da HospitalApplicationService

## Status
Aceito

## Contexto

Durante a análise do MediConnect, foi identificado que a classe `HospitalApplicationService` concentrava responsabilidades diferentes.

Além de coordenar os fluxos principais do sistema, como agendamento de consultas e solicitação de exames, a classe também conhecia detalhes relacionados à comunicação com os pacientes.

No fluxo de agendamento, a própria `HospitalApplicationService` definia o canal de comunicação e construía a mensagem enviada ao paciente. Situação semelhante ocorria no fluxo de exames.

Essa concentração de responsabilidades reduzia a coesão da classe e dificultava futuras alterações na forma de envio das notificações.

A necessidade identificada foi separar a responsabilidade de comunicação com pacientes da responsabilidade de coordenação dos processos hospitalares.

## Alternativas consideradas

1. **Manter as notificações dentro da `HospitalApplicationService`**  
   Evitaria alterações na estrutura existente, porém manteria a classe responsável tanto pelas regras do fluxo hospitalar quanto pelos detalhes de comunicação.

2. **Criar serviços separados para consultas, exames e internações**  
   Permitiria uma divisão maior das responsabilidades, porém exigiria uma refatoração mais ampla do projeto e aumentaria a quantidade de mudanças necessárias para resolver o problema identificado.

3. **Delegar os detalhes das notificações à `NotificationService`**  
   Permite manter a `HospitalApplicationService` responsável pela coordenação dos fluxos e concentrar na `NotificationService` os detalhes de comunicação com os pacientes.

## Decisão

Foi decidido delegar os detalhes das notificações para a `NotificationService`.

Foram utilizados os métodos:

- `notifyAppointmentScheduled()`;
- `notifyExamStatus()`.

A `HospitalApplicationService` continua responsável por coordenar os processos hospitalares, porém não precisa mais construir diretamente as mensagens de consulta e exame.

A mudança está relacionada principalmente ao princípio **Single Responsibility Principle (SRP)**, pois cada classe passa a possuir uma responsabilidade mais bem definida.

## Consequências

### Positivas

- A `HospitalApplicationService` possui menos responsabilidades.
- Os detalhes relacionados às notificações ficam concentrados na `NotificationService`.
- A manutenção das mensagens e canais de comunicação fica mais localizada.
- A organização do código fica mais clara.
- Alterações futuras relacionadas às notificações tendem a gerar menos impacto no serviço principal.

### Negativas / trade-offs

- A `HospitalApplicationService` passa a depender explicitamente da `NotificationService`.
- A separação adiciona uma nova interação entre classes.
- A refatoração não separa todos os fluxos hospitalares em serviços independentes, mantendo parte da coordenação concentrada na `HospitalApplicationService`.

## Evidências relacionadas

- **Requisito(s):** RF01 — marcar consultas; RF02 — solicitar exames; RF04 — avisar o paciente.
- **Classe/pacote/componente:** `service/HospitalApplicationService.java` e `service/NotificationService.java`.
- **Teste:** `src/test/java/br/edu/mediconnect/service/HospitalApplicationServiceTest.java`.
- **Documentação:** `docs/aula04-refatoracao.md`.
- **Issue/PR:** evidência registrada no histórico Git da atividade.