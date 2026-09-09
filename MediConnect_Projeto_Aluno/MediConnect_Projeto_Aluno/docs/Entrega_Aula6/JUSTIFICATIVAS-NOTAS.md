# Justificativas das Notas da Matriz — MediConnect (Aula 06)

## Objetivo

Registrar a justificativa de cada nota atribuída na matriz de decisão arquitetural do MediConnect (arquivo `MATRIZ-DECISAO.md`).

Alternativas avaliadas:
- **Alternativa A** — manter a arquitetura atual (camadas simples).
- **Alternativa B** — arquitetura orientada a eventos (message broker), considerada e não adotada em `ADR-0001`.
- **Alternativa C** — microsserviços por domínio (agendamento/exames, laboratório, convênio).

---

## Tabela de justificativas

| Critério | Alternativa | Nota | Justificativa | Evidência no projeto |
|---|---|---:|---|---|
| Desempenho | Alternativa A | 5 | Todas as chamadas hoje são locais e síncronas, sem I/O de rede real, o que mantém a latência baixa. | `HospitalApplicationService.schedule(...)` e `requestExam(...)` chamam diretamente `InMemoryPatientRepository`/`InMemoryAppointmentRepository` e os adapters locais. |
| Desempenho | Alternativa B | 3 | Mensageria assíncrona desacoplaria picos de carga, mas introduziria latência de publish/consume e overhead de serialização que hoje não existem. | Nenhum broker está implementado no projeto; não há dependência de mensageria declarada no `pom.xml`. |
| Desempenho | Alternativa C | 2 | Comunicação entre serviços via rede aumentaria a latência e os pontos de falha de cada chamada (ex.: agendamento dependeria de rede até o serviço de laboratório/convênio). | Hoje `LabAdapter`/`HealthPlanAdapter` chamam classes locais (`LabXClient`, `LegacyHealthPlanApi`) sem rede; em microsserviços essas chamadas passariam a ser remotas. |
| Segurança | Alternativa A | 3 | A arquitetura em camadas não impede aplicar TLS e mascarar dados sensíveis nos logs, mas isso ainda não foi implementado. | `NotificationService.notify(...)` e `WhatsappHospitalApi.sendMessage(...)` imprimem telefone e texto em texto plano via `System.out`. |
| Segurança | Alternativa B | 3 | Um broker permitiria isolar credenciais e usar TLS no canal de mensageria, mas adiciona uma nova superfície de ataque (broker, tópicos, controle de acesso a filas) que o projeto não gerencia hoje. | Não há nenhuma configuração de segurança para mensageria no projeto (sem broker declarado no `pom.xml`). |
| Segurança | Alternativa C | 3 | Fronteiras de serviço permitiriam controle de acesso por serviço (ex.: só o serviço de convênio acessa `LegacyHealthPlanApi`), mas exigiria gestão de segredos e autenticação entre serviços, inexistente hoje. | `HealthPlanAdapter` e `LabAdapter` rodam hoje no mesmo processo, sem fronteira de rede a proteger. |
| Manutenibilidade | Alternativa A | 3 | O código está dividido em pacotes (`model`, `service`, `repository`, `patterns`), mas com acoplamentos indevidos que dificultam manutenção. | `HealthPlanAdapter extends LegacyHealthPlanApi` (herança em vez de composição); `PartnerFamilyFactory` retorna `Object` sem interface de produto comum. |
| Manutenibilidade | Alternativa B | 3 | Eventos tendem a desacoplar produtores e consumidores a longo prazo, mas exigiriam reescrever `HospitalPublisher`/observers como contratos de evento estruturados. | `HospitalObserver.update(String entityId, String event)` usa apenas *strings* soltas, sem payload estruturado — teria que ser redesenhado para eventos reais. |
| Manutenibilidade | Alternativa C | 2 | Múltiplos serviços/repositórios aumentariam o esforço de manutenção para uma equipe pequena de alunos. | O projeto é hoje um único módulo Maven (`pom.xml`, `artifactId=mediconnect`), sem separação de deploys; dividir em microsserviços multiplicaria esse esforço. |
| Disponibilidade/Confiabilidade | Alternativa A | 2 | Falha síncrona interrompe o fluxo principal, e o `HospitalPublisher` só suporta um observer por vez, então nem todo observer cadastrado é notificado. | `HospitalPublisher.subscribe(o)` sobrescreve o campo único `observer`; `HospitalApplicationService` registra `PatientNotificationObserver` e depois `AuditObserver` no construtor, e apenas o último permanece ativo — reproduzido em `DiagnosticChecks.java`. |
| Disponibilidade/Confiabilidade | Alternativa B | 4 | Uma fila permitiria reentrega em caso de falha e desacoplaria a falha de um consumidor do fluxo de negócio principal. | Não há implementação hoje; é o comportamento característico de sistemas de mensageria com fila e reentrega, considerado como trade-off nas Alternativas do `ADR-0001-arquitetura.md`. |
| Disponibilidade/Confiabilidade | Alternativa C | 3 | O isolamento de falha por serviço ajudaria, mas exigiria mecanismos como *circuit breaker* e orquestração, que o projeto não possui. | Nenhuma implementação de tolerância a falha existe hoje; `HealthPlanAdapter` e `LabAdapter` não tratam exceção nem timeout. |
| Complexidade operacional | Alternativa A | 5 | O sistema roda com um único comando de build e execução, sem infraestrutura adicional. | `README.md`, seção "Execução": `mvn compile` seguido de `java -cp target/classes br.edu.mediconnect.Main`. |
| Complexidade operacional | Alternativa B | 2 | Exigiria broker de mensagens, monitoramento de filas/tópicos e configuração adicional de infraestrutura, hoje inexistentes. | Nenhuma infraestrutura de mensageria consta no projeto (não há dependência de broker no `pom.xml`). |
| Complexidade operacional | Alternativa C | 2 | Múltiplos serviços implicariam múltiplos deploys, containers e descoberta de serviço. | O projeto é hoje um único artefato Maven (`pom.xml`), sem qualquer configuração de orquestração/contêineres. |
| Custo/esforço de migração | Alternativa A | 5 | Não há custo de migração: é a arquitetura já existente e funcional. | `README.md` descreve o projeto como "sistema legado em evolução", recomendando cautela em mudanças. |
| Custo/esforço de migração | Alternativa B | 2 | Exigiria reescrever `HospitalPublisher`/observers como produtores/consumidores de eventos e introduzir um broker do zero. | `HospitalPublisher.java` hoje não tem nenhuma abstração de fila/tópico — a mudança partiria do zero. |
| Custo/esforço de migração | Alternativa C | 2 | Exigiria separar `model`/`service`/`repository` por domínio e criar contratos de API entre os novos serviços. | Hoje `Patient`, `Appointment`, `ExamRequest` e `Admission` compartilham o mesmo módulo e os mesmos repositórios em memória (`InMemoryPatientRepository`, `InMemoryAppointmentRepository`). |

---

## Modelo individual de justificativa (exemplo)

### Critério

Disponibilidade/Confiabilidade

### Alternativa

Alternativa A — manter a arquitetura atual (camadas simples)

### Nota atribuída

2

### Justificativa

O fluxo de notificação depende do padrão Observer implementado em `HospitalPublisher`, mas essa classe guarda apenas **um único** observer por vez em vez de uma lista. Como `HospitalApplicationService` registra dois observers no construtor (`PatientNotificationObserver` e, em seguida, `AuditObserver`), o segundo sobrescreve o primeiro e o paciente deixa de ser notificado por esse canal. Além disso, as chamadas são síncronas: uma exceção em um observer poderia interromper o próprio fluxo de agendamento/exame.

### Evidência utilizada

`HospitalPublisher.java` — campo `private HospitalObserver observer;` (não é uma lista) e método `subscribe(HospitalObserver o){ observer = o; }`, que sobrescreve qualquer observer anterior. Comportamento reproduzido em `src/test/java/br/edu/mediconnect/DiagnosticChecks.java`.

---

## O que foi considerado evidência

Foram utilizadas, ao longo da tabela acima:

- classes e métodos específicos (ex.: `HospitalPublisher`, `NotificationService.notify(...)`);
- pacotes (ex.: `patterns/adapter`, `patterns/observer`);
- arquivos de configuração (`pom.xml`);
- documentação do próprio projeto (`README.md`, `ADR-0001-arquitetura.md`);
- ausência de implementação (ex.: nenhuma dependência de broker no `pom.xml`) como evidência de que um requisito daquela alternativa ainda não existe;
- um teste/diagnóstico já presente no repositório (`DiagnosticChecks.java`) que reproduz o comportamento descrito.

## Observação sobre o "Não fazer"

As justificativas acima evitam frases genéricas como "é mais seguro" ou "é mais moderno" isoladas: cada nota está amarrada a uma classe, método, arquivo de configuração ou comportamento observável do próprio MediConnect.
