# Justificativas das Notas da Matriz — MediConnect (Aula 06)

## Objetivo

Registrar a justificativa das notas atribuídas na matriz de decisão arquitetural do MediConnect.

A matriz completa está registrada em:

`docs/Entrega_Aula6/MATRIZ-DECISAO.md`

As alternativas avaliadas foram:

- **Alternativa A** — manter a arquitetura atual em camadas simples;
- **Alternativa B** — adotar uma arquitetura orientada a eventos;
- **Alternativa C** — separar o sistema em microsserviços.

A arquitetura atual e a decisão de mantê-la estão relacionadas ao ADR:

`docs/adr/ADR-0001.md`

---

## 1. Desempenho

### Alternativa A — Nota 5

A arquitetura atual utiliza principalmente chamadas locais e síncronas dentro do mesmo processo.

Os repositórios utilizados são estruturas em memória e as integrações externas do projeto são simuladas por classes Java locais.

Isso reduz o custo de comunicação entre os componentes no contexto atual do sistema.

**Evidências:**

- `HospitalApplicationService.schedule(...)`;
- `HospitalApplicationService.requestExam(...)`;
- `InMemoryPatientRepository`;
- `InMemoryAppointmentRepository`;
- `HealthPlanAdapter`;
- `LabAdapter`.

---

### Alternativa B — Nota 3

Uma arquitetura orientada a eventos poderia reduzir o acoplamento e permitir que algumas operações secundárias fossem processadas de forma assíncrona.

Entretanto, a introdução de mensageria adicionaria novas etapas ao fluxo, como publicação, consumo e processamento dos eventos.

Como o MediConnect atualmente não apresenta um problema comprovado de escala ou volume elevado de eventos, o benefício de desempenho não é suficiente para justificar a mudança neste momento.

**Evidência:**

Não existe atualmente infraestrutura ou dependência de mensageria configurada no `pom.xml`.

---

### Alternativa C — Nota 2

Uma arquitetura de microsserviços exigiria comunicação entre processos ou serviços independentes.

Operações que atualmente acontecem através de chamadas locais poderiam passar a depender de comunicação externa entre serviços.

Isso adicionaria maior custo de comunicação e mais pontos sujeitos a atraso ou indisponibilidade.

**Evidência:**

Atualmente os componentes fazem parte do mesmo módulo Maven e utilizam chamadas diretas entre classes Java.

---

## 2. Segurança

### Alternativa A — Nota 3

A arquitetura atual permite implementar melhorias de segurança sem exigir uma mudança completa de estilo arquitetural.

Entretanto, ainda existem pontos em que informações como telefone, e-mail e mensagens podem aparecer diretamente na saída da aplicação.

Por isso, a arquitetura atual atende parcialmente ao critério, mas ainda precisa de melhorias.

**Evidências:**

- `NotificationService`;
- `WhatsappHospitalApi`;
- uso de `System.out` em partes do fluxo de notificação.

---

### Alternativa B — Nota 3

Uma arquitetura orientada a eventos poderia oferecer melhor isolamento entre produtores e consumidores.

Entretanto, a introdução de um broker também exigiria novos controles de segurança, como autenticação, autorização e proteção dos canais de comunicação.

Isso significa que a mudança arquitetural, por si só, não resolveria os problemas de segurança.

**Evidência:**

O projeto atualmente não possui infraestrutura de mensageria configurada.

---

### Alternativa C — Nota 3

Microsserviços poderiam permitir maior isolamento entre responsabilidades e serviços.

Por outro lado, seriam necessários mecanismos de autenticação, autorização e proteção da comunicação entre os serviços.

Como esses mecanismos ainda não existem no MediConnect, a adoção de microsserviços não representaria automaticamente uma melhoria de segurança.

**Evidência:**

Atualmente todos os principais componentes executam dentro do mesmo projeto Maven.

---

## 3. Manutenibilidade

### Alternativa A — Nota 3

O projeto possui alguma separação de responsabilidades através de pacotes como:

- `model`;
- `service`;
- `repository`;
- `patterns`.

Entretanto, ainda existem pontos de acoplamento e responsabilidades que podem ser melhorados.

Por isso, a arquitetura atual apresenta uma manutenibilidade adequada para o projeto, mas ainda possui limitações.

**Evidências:**

- `HealthPlanAdapter` possui forte dependência da implementação legada;
- `PartnerFamilyFactory` utiliza retornos genéricos do tipo `Object`;
- `HospitalApplicationService` ainda coordena várias dependências.

---

### Alternativa B — Nota 3

Eventos podem reduzir o acoplamento direto entre produtores e consumidores.

Entretanto, seria necessário definir contratos de eventos, organizar produtores e consumidores e adaptar o mecanismo atual de observers.

Essa mudança poderia melhorar a manutenção no futuro, mas também aumentaria a complexidade do projeto atual.

**Evidência:**

O contrato atual do Observer utiliza:

```java
update(String entityId, String event)
```

ou seja, ainda utiliza informações simples em vez de uma estrutura própria para eventos distribuídos.

---

### Alternativa C — Nota 2

A separação em microsserviços poderia melhorar o isolamento entre áreas do sistema.

Entretanto, para uma equipe pequena, também aumentaria a quantidade de projetos, interfaces e pontos de integração que precisariam ser mantidos.

No contexto atual, isso aumentaria significativamente o esforço de manutenção.

**Evidência:**

O MediConnect é atualmente um único módulo Maven identificado pelo `pom.xml`.

---

## 4. Disponibilidade e confiabilidade

### Alternativa A — Nota 2

A arquitetura atual apresenta uma limitação importante no mecanismo de Observer.

O `HospitalPublisher` armazena apenas um observer:

```java
private HospitalObserver observer;
```

O método:

```java
public void subscribe(HospitalObserver o) {
    observer = o;
}
```

substitui o observer registrado anteriormente.

No construtor da `HospitalApplicationService`, são registrados primeiro:

```java
new PatientNotificationObserver()
```

e depois:

```java
new AuditObserver()
```

Com isso, apenas o último observer permanece registrado.

Esse comportamento reduz a confiabilidade da entrega dos eventos.

Além disso, várias operações são executadas de forma síncrona, fazendo com que uma falha em uma dependência possa afetar o fluxo principal.

**Evidências:**

- `HospitalPublisher.java`;
- `HospitalApplicationService.java`;
- `DiagnosticChecks.java`.

---

### Alternativa B — Nota 4

Uma arquitetura orientada a eventos poderia melhorar a confiabilidade da comunicação entre componentes.

Soluções de mensageria podem permitir recursos como:

- armazenamento temporário dos eventos;
- processamento assíncrono;
- reprocessamento em caso de falha;
- menor dependência direta entre produtor e consumidor.

Entretanto, esses benefícios dependeriam da configuração e implementação corretas da infraestrutura.

**Evidência:**

Esses mecanismos não existem atualmente no projeto e fariam parte de uma nova solução arquitetural.

---

### Alternativa C — Nota 3

A separação em serviços independentes poderia evitar que determinadas falhas afetassem todo o sistema.

Entretanto, sistemas distribuídos também introduzem novos tipos de falha, principalmente relacionados à comunicação entre serviços.

Seriam necessários mecanismos adicionais de tolerância a falhas e tratamento de indisponibilidade.

**Evidência:**

Atualmente `HealthPlanAdapter` e `LabAdapter` executam localmente e não possuem mecanismos específicos de tolerância a falhas distribuídas.

---

## 5. Complexidade operacional

### Alternativa A — Nota 5

A arquitetura atual possui baixa complexidade operacional.

O sistema utiliza:

- um único projeto Maven;
- um único processo de aplicação;
- compilação e testes através de Maven;
- nenhuma infraestrutura externa obrigatória.

A execução e a manutenção são simples para a equipe atual.

**Evidências:**

- `pom.xml`;
- estrutura atual do repositório;
- comando `mvn clean test`.

---

### Alternativa B — Nota 2

Uma arquitetura orientada a eventos exigiria a introdução e manutenção de uma infraestrutura de mensageria.

Além do código da aplicação, seria necessário administrar:

- broker;
- filas ou tópicos;
- produtores;
- consumidores;
- configuração e monitoramento da comunicação.

Isso aumentaria significativamente a complexidade operacional comparada ao projeto atual.

**Evidência:**

Não existe atualmente infraestrutura de mensageria no MediConnect.

---

### Alternativa C — Nota 2

Microsserviços exigiriam a operação de vários serviços independentes.

Isso aumentaria a necessidade de:

- configurar os diferentes serviços;
- controlar suas versões;
- realizar múltiplas execuções ou deploys;
- monitorar a comunicação entre eles.

Para o contexto acadêmico e para a equipe atual, isso representa uma complexidade elevada.

**Evidência:**

Atualmente o sistema é desenvolvido e executado como um único artefato Maven.

---

## 6. Custo e esforço de migração

### Alternativa A — Nota 5

A Alternativa A mantém a estrutura já utilizada pelo projeto.

Portanto, não exige uma migração arquitetural completa.

As melhorias identificadas podem ser implementadas gradualmente dentro da estrutura existente.

**Evidência:**

O código atual já funciona dentro da arquitetura em camadas simples descrita no projeto.

---

### Alternativa B — Nota 2

A migração para uma arquitetura orientada a eventos exigiria mudanças importantes.

Seria necessário:

- introduzir uma infraestrutura de mensageria;
- transformar partes do mecanismo atual de eventos;
- criar produtores e consumidores;
- definir estruturas próprias para os eventos;
- alterar os fluxos atualmente síncronos.

Isso representa um esforço significativo para o contexto atual.

**Evidência:**

O `HospitalPublisher` atual possui apenas um mecanismo simples de Observer e não possui abstração de filas, tópicos ou broker.

---

### Alternativa C — Nota 2

A migração para microsserviços exigiria separar partes que atualmente fazem parte do mesmo projeto.

Seria necessário definir:

- limites entre os serviços;
- contratos de comunicação;
- responsabilidades de cada serviço;
- mecanismos de integração;
- formas independentes de execução.

O esforço de reorganização seria elevado para uma equipe pequena e um projeto semestral.

**Evidência:**

Entidades como `Patient`, `Appointment`, `ExamRequest` e `Admission` fazem parte atualmente do mesmo módulo.

---

## 7. Resumo das justificativas

| Critério | Alternativa A | Alternativa B | Alternativa C |
|---|---:|---:|---:|
| Desempenho | 5 | 3 | 2 |
| Segurança | 3 | 3 | 3 |
| Manutenibilidade | 3 | 3 | 2 |
| Disponibilidade/Confiabilidade | 2 | 4 | 3 |
| Complexidade operacional | 5 | 2 | 2 |
| Custo/esforço de migração | 5 | 2 | 2 |

As notas utilizadas aqui correspondem às notas registradas em:

`docs/Entrega_Aula6/MATRIZ-DECISAO.md`

---

## 8. Exemplo de justificativa detalhada

### Critério

Disponibilidade/Confiabilidade

### Alternativa

Alternativa A — manter a arquitetura atual.

### Nota atribuída

2

### Justificativa

O fluxo atual utiliza o padrão Observer através do `HospitalPublisher`.

Entretanto, a implementação mantém apenas um único `HospitalObserver`.

Como a `HospitalApplicationService` registra dois observers sequencialmente, o segundo substitui o primeiro.

Com isso, nem todos os interessados recebem os eventos esperados.

Esse problema reduz a confiabilidade da implementação atual e justifica uma nota menor nesse critério.

### Evidência utilizada

Arquivo:

`src/main/java/br/edu/mediconnect/patterns/observer/HospitalPublisher.java`

Trechos:

```java
private HospitalObserver observer;
```

e:

```java
public void subscribe(HospitalObserver o) {
    observer = o;
}
```

O comportamento também é observado no diagnóstico existente em:

`src/test/java/br/edu/mediconnect/DiagnosticChecks.java`

---

## 9. Evidências utilizadas

As justificativas foram baseadas em evidências existentes no próprio MediConnect, como:

- classes e métodos específicos;
- estrutura dos pacotes;
- `pom.xml`;
- implementação dos adapters;
- implementação do Observer;
- documentação do projeto;
- ausência de infraestrutura distribuída;
- testes e diagnósticos existentes no repositório.

O objetivo foi evitar justificativas genéricas como:

- "é mais moderno";
- "é mais seguro";
- "é melhor".

Cada nota foi relacionada ao contexto e à estrutura atual do MediConnect.

---

## 10. Conclusão

A matriz mostra que nenhuma alternativa é melhor em todos os critérios.

A arquitetura orientada a eventos apresenta vantagens importantes para confiabilidade e desacoplamento.

Microsserviços podem oferecer maior isolamento entre domínios em sistemas maiores.

Entretanto, considerando o tamanho atual do MediConnect, a equipe, o tempo disponível e a ausência de requisitos comprovados de grande escala, a manutenção da arquitetura atual apresenta menor custo e menor complexidade.

Por isso, a decisão registrada para esta etapa é manter a arquitetura atual e realizar melhorias internas nos pontos identificados.