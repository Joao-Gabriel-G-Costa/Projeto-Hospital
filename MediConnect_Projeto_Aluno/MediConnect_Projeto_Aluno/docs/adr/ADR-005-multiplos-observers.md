# ADR-005 — Suporte a múltiplos observers no HospitalPublisher

## Status

Aceito.

## Contexto

Durante a análise arquitetural do MediConnect foi identificado um problema no mecanismo de publicação de eventos utilizado pelo sistema.

A classe `HospitalPublisher` utiliza atualmente apenas uma variável para armazenar um observer:

```java
private HospitalObserver observer;
```

O método de inscrição também substitui diretamente o observer existente:

```java
public void subscribe(HospitalObserver o) {
    observer = o;
}
```

No construtor de `HospitalApplicationService`, são registrados dois observers:

```java
publisher.subscribe(new PatientNotificationObserver());
publisher.subscribe(new AuditObserver());
```

Como apenas uma referência é armazenada, o segundo cadastro substitui o primeiro.

Dessa forma, quando um evento é publicado, somente o último observer registrado recebe a atualização.

Esse comportamento afeta a confiabilidade do mecanismo de eventos do MediConnect e está relacionado ao requisito:

**RNF03 — Confiabilidade/Disponibilidade: eventos relevantes devem ser entregues corretamente aos componentes interessados sem comprometer o fluxo principal.**

---

## Alternativas consideradas

### Alternativa 1 — Manter apenas um observer

Manter a implementação atual do `HospitalPublisher`.

#### Benefícios

- nenhuma alteração de código;
- implementação simples.

#### Custos

- apenas um interessado pode receber cada evento;
- novos cadastros substituem observers anteriores;
- o comportamento esperado pelo padrão Observer não é atendido adequadamente no contexto atual;
- reduz a confiabilidade das notificações internas.

Essa alternativa foi descartada porque mantém o problema identificado.

---

### Alternativa 2 — Criar um observer composto

Criar uma nova classe responsável por agrupar vários observers e cadastrá-la como único observer dentro do `HospitalPublisher`.

#### Benefícios

- permitiria manter apenas uma referência dentro do publisher;
- poderia agrupar diferentes comportamentos.

#### Custos

- adicionaria uma nova classe apenas para contornar a limitação existente;
- aumentaria a complexidade;
- o gerenciamento dos observers continuaria fora do componente responsável pela publicação.

Essa alternativa foi considerada desnecessariamente complexa para o problema atual.

---

### Alternativa 3 — Armazenar uma coleção de observers

Alterar o `HospitalPublisher` para manter uma coleção de objetos que implementam `HospitalObserver`.

O método `subscribe(...)` passa a adicionar observers à coleção, em vez de substituir o anterior.

Ao publicar um evento, o publisher percorre os observers cadastrados e chama:

```java
update(...)
```

em cada um deles.

#### Benefícios

- permite múltiplos interessados no mesmo evento;
- preserva a estrutura existente do padrão Observer;
- exige uma alteração pequena e localizada;
- facilita a inclusão de novos observers no futuro;
- resolve diretamente o problema identificado.

#### Custos

- o publisher passa a gerenciar uma coleção;
- a ordem de notificação passa a depender da ordem de inscrição;
- uma falha em um observer ainda poderá exigir tratamento específico em uma evolução futura.

---

## Decisão

Foi decidido utilizar a **Alternativa 3 — armazenar uma coleção de observers no `HospitalPublisher`**.

A classe será alterada para manter vários objetos que implementam `HospitalObserver`.

O método:

```java
subscribe(...)
```

passará a adicionar o observer à coleção.

O método:

```java
publish(...)
```

percorrerá todos os observers cadastrados e enviará o evento para cada um.

A mudança será realizada dentro do mecanismo já existente, sem introduzir mensageria, novos frameworks ou uma mudança completa da arquitetura.

A decisão foi escolhida porque resolve o problema identificado com menor impacto sobre a estrutura atual do MediConnect.

---

## Consequências

### Positivas

- `PatientNotificationObserver` e `AuditObserver` poderão receber o mesmo evento.
- Novos observers poderão ser adicionados sem substituir os existentes.
- O mecanismo de eventos ficará mais coerente com o padrão Observer.
- A confiabilidade da entrega dos eventos internos será melhorada.
- A alteração ficará concentrada no `HospitalPublisher`.
- Não será necessária nova infraestrutura.

### Negativas / trade-offs

- O `HospitalPublisher` passa a manter uma coleção de observers.
- A ordem de execução seguirá a ordem de inscrição.
- Caso um observer lance uma exceção, poderá ser necessário definir futuramente uma estratégia de isolamento de falhas.
- O sistema continua utilizando eventos síncronos dentro do mesmo processo.

---

## Relação com a arquitetura

A decisão não altera o estilo arquitetural principal definido para o MediConnect.

A arquitetura continua sendo uma aplicação em camadas simples.

A mudança ocorre apenas no mecanismo interno de publicação de eventos:

```text
Antes:

HospitalPublisher
       ↓
HospitalObserver
       ↓
apenas um observer armazenado
```

Após a alteração:

```text
HospitalPublisher
       ↓
List<HospitalObserver>
       ↓
 ┌───────────────┬───────────────┐
 ↓               ↓
Patient        Audit
Observer       Observer
```

---

## Evidências relacionadas

- **Requisito:** RNF03 — Confiabilidade/Disponibilidade.
- **Componente:** `HospitalPublisher`.
- **Interface:** `HospitalObserver`.
- **Observers:** `PatientNotificationObserver` e `AuditObserver`.
- **Código:** `src/main/java/br/edu/mediconnect/patterns/observer/HospitalPublisher.java`.
- **Diagnóstico existente:** `src/test/java/br/edu/mediconnect/DiagnosticChecks.java`.
- **Teste automatizado:** `src/test/java/br/edu/mediconnect/patterns/observer/HospitalPublisherTest.java`.
- **Matriz de rastreabilidade:** `docs/aula07-rastreabilidade.md`.
- **Issue/branch/commits/PR:** evidências registradas no histórico Git da atividade.