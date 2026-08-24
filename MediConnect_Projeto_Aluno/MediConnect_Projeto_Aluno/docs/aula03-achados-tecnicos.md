# Aula 03 — Achados técnicos do MediConnect

## 1. Responsabilidades excessivas em HospitalApplicationService

**Classificação:** causa provável.

A classe `HospitalApplicationService` coordena diferentes processos do sistema, incluindo agendamento de consultas, solicitação de exames, internações, publicação de eventos e acionamento de notificações.

Além de coordenar os processos, a versão original também conhecia detalhes como o canal utilizado e a construção das mensagens enviadas ao paciente.

**Consequência:** baixa coesão e maior dificuldade de manutenção, pois mudanças na comunicação com o paciente poderiam exigir alterações em uma classe responsável pelos processos hospitalares.

**Prioridade:** alta.

---

## 2. Criação direta de integrações externas

**Classificação:** sintoma.

Dentro de `requestExam()`, a classe cria diretamente instâncias de `HealthPlanAdapter` e `LabAdapter`.

```java
new HealthPlanAdapter()
new LabAdapter()
```

**Causa provável:** ausência de abstração ou injeção das dependências utilizadas pelo serviço.

**Consequência:** aumenta o acoplamento entre o fluxo de exames e implementações específicas das integrações, dificultando testes e substituição de parceiros.

**Prioridade:** média.

---

## 3. Implementação limitada do Observer

**Classificação:** causa provável.

A classe `HospitalPublisher` possui apenas um campo:

```java
private HospitalObserver observer;
```

Cada chamada de `subscribe()` substitui o observador anterior.

A `HospitalApplicationService` executa:

```java
publisher.subscribe(new PatientNotificationObserver());
publisher.subscribe(new AuditObserver());
```

Com isso, o segundo observador substitui o primeiro.

**Consequência:** apesar do nome e da intenção de Observer, apenas um interessado recebe o evento.

**Prioridade:** alta.

---

## 4. Strategy parcialmente ignorado

**Classificação:** sintoma.

O `TriageEngine` possui uma `PriorityStrategy`, porém existem regras fixas dentro do próprio método `calculate()`:

```java
if ("EMERGENCY".equals(a.type)) return 100;
if ("RETURN".equals(a.type)) return 20;
```

A estratégia configurada só é utilizada em outros casos.

**Consequência:** parte da lógica de prioridade continua acoplada ao `TriageEngine`, reduzindo o benefício do padrão Strategy.

**Prioridade:** média.

---

## 5. Factory com regras condicionais

**Classificação:** sintoma.

A `AppointmentFactory` cria sempre o mesmo tipo de objeto `Appointment` e utiliza condicionais apenas para alterar a prioridade.

Isso indica que o nome Factory pode sugerir uma abstração maior do que a implementação realmente oferece.

**Consequência:** aumenta a complexidade conceitual sem necessariamente reduzir o acoplamento.

**Prioridade:** baixa.

---

## 6. Modelos com atributos públicos

**Classificação:** causa provável.

Classes como `Patient`, `Appointment` e `ExamRequest` expõem seus atributos diretamente como `public`.

**Consequência:** qualquer classe pode alterar o estado dos objetos sem validação, diminuindo o encapsulamento.

**Prioridade:** média.

---

## Achado priorizado

Para a primeira refatoração foi priorizada a responsabilidade de notificações existente em `HospitalApplicationService`.

A escolha foi realizada porque a mudança possui baixo risco, é diretamente relacionada à coesão e ao princípio da responsabilidade única e pode ser realizada preservando o comportamento atual.
