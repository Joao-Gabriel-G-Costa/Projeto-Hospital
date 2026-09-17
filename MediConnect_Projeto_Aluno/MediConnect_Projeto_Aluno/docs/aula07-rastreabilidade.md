# Aula 07 — Matriz de Rastreabilidade

## Objetivo

Relacionar requisitos, decisões de design, componentes afetados e evidências existentes no projeto MediConnect.

A matriz permite verificar como uma necessidade do sistema é refletida em uma decisão técnica e como essa decisão pode ser localizada e validada no código e na documentação.

---

## Matriz de rastreabilidade

| Requisito | Decisão | Componente / Artefato | Evidência |
|---|---|---|---|
| RF01 — O sistema deve permitir marcar consultas. | Manter o fluxo de agendamento coordenado pela `HospitalApplicationService`. | `HospitalApplicationService` | `src/main/java/br/edu/mediconnect/service/HospitalApplicationService.java` |
| RF01 — O sistema deve permitir marcar consultas. | Validar o comportamento do agendamento através de teste automatizado. | `HospitalApplicationServiceTest` | `src/test/java/br/edu/mediconnect/service/HospitalApplicationServiceTest.java` |
| RF02 — O sistema deve permitir solicitar exames. | Utilizar adapters para isolar a comunicação com convênio e laboratório. | `HealthPlanAdapter`, `LabAdapter` | `src/main/java/br/edu/mediconnect/patterns/adapter/` |
| RF04 — O sistema deve avisar o paciente quando necessário. | Centralizar a responsabilidade de comunicação na `NotificationService`. | `NotificationService` | `src/main/java/br/edu/mediconnect/service/NotificationService.java` |
| RNF01 — Segurança. | Identificar pontos em que informações sensíveis podem ser expostas e tratá-los como evolução arquitetural. | `NotificationService`, `WhatsappHospitalApi` | `docs/Entrega_Aula6/README-Aula06.md` |
| RNF02 — Desempenho. | Manter chamadas locais e síncronas no contexto atual do projeto. | Arquitetura atual | `docs/Entrega_Aula6/MATRIZ-DECISAO.md` |
| RNF03 — Confiabilidade/Disponibilidade. | Permitir que múltiplos observers recebam o mesmo evento. | `HospitalPublisher` | `docs/adr/ADR-005-multiplos-observers.md` |
| RNF03 — Confiabilidade/Disponibilidade. | Armazenar observers em uma coleção em vez de manter somente uma referência. | `HospitalPublisher` | `src/main/java/br/edu/mediconnect/patterns/observer/HospitalPublisher.java` |
| RNF03 — Confiabilidade/Disponibilidade. | Validar automaticamente que todos os observers inscritos são notificados. | `HospitalPublisherTest` | `src/test/java/br/edu/mediconnect/patterns/observer/HospitalPublisherTest.java` |
| RNF04 — Manutenibilidade. | Manter integrações externas isoladas através de adapters. | `HealthPlanAdapter`, `LabAdapter` | `docs/Entrega_Aula6/JUSTIFICATIVAS-NOTAS.md` |
| RNF04 — Manutenibilidade. | Separar detalhes das notificações da coordenação dos processos hospitalares. | `NotificationService`, `HospitalApplicationService` | `docs/adr/ADR-004-refatoracao-responsabilidades.md` |

---

## Rastreabilidade da decisão principal da Aula 07

A principal decisão trabalhada nesta atividade está relacionada ao RNF03.

O fluxo de rastreabilidade é:

```text
RNF03 — Confiabilidade/Disponibilidade
        ↓
Problema identificado:
HospitalPublisher armazena apenas um observer
        ↓
ADR-005:
suporte a múltiplos observers
        ↓
HospitalPublisher
        ↓
List<HospitalObserver>
        ↓
HospitalPublisherTest
        ↓
mvn clean test
        ↓
BUILD SUCCESS