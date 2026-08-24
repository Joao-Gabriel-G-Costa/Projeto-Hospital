# Aula 05 — Backlog técnico do MediConnect

O backlog abaixo transforma os principais achados técnicos e hipóteses levantados durante a análise do MediConnect em decisões de evolução verificáveis.

## Item 1 — Corrigir suporte a múltiplos observadores

**Prioridade:** alta
**Origem:** risco técnico

### Problema

O `HospitalPublisher` mantém somente um observador. Ao realizar duas chamadas de `subscribe()`, o segundo objeto substitui o primeiro.

### Decisão de design

Permitir que o publisher mantenha uma coleção de observadores inscritos.

### Critérios de aceitação

* mais de um observador pode ser registrado;
* todos os observadores recebem o mesmo evento;
* novos observadores podem ser adicionados sem alteração no método `publish()`.

---

## Item 2 — Desacoplar integrações do fluxo de exames

**Prioridade:** alta
**Origem:** risco de manutenção e integração

### Problema

`HospitalApplicationService` instancia diretamente `HealthPlanAdapter` e `LabAdapter`.

### Decisão de design

Avaliar abstrações para autorização e laboratório e fornecer essas dependências ao serviço.

### Critérios de aceitação

* o fluxo de exames não cria diretamente clientes externos;
* uma implementação de autorização pode ser substituída;
* uma implementação de laboratório pode ser substituída;
* comportamento atual permanece preservado.

---

## Item 3 — Revisar estratégia de priorização

**Prioridade:** média
**Origem:** inconsistência de design

### Problema

O `TriageEngine` utiliza Strategy, mas ainda mantém regras fixas para `EMERGENCY` e `RETURN`.

### Decisão de design

Centralizar as regras de cálculo de prioridade nas estratégias apropriadas.

### Critérios de aceitação

* o motor não contém regras específicas de tipos de consulta;
* diferentes estratégias podem ser substituídas;
* os resultados definidos para prioridades existentes são preservados.

---

## Item 4 — Melhorar encapsulamento dos modelos

**Prioridade:** média
**Origem:** risco de integridade dos dados

### Problema

As entidades possuem diversos atributos públicos e modificáveis diretamente.

### Decisão de design

Avaliar encapsulamento progressivo dos estados das entidades.

### Critérios de aceitação

* atributos críticos não podem ser alterados arbitrariamente;
* alterações de estado relevantes passam por métodos explícitos;
* funcionalidades atuais continuam funcionando.

---

## Item 5 — Revisar o uso de Factory

**Prioridade:** baixa
**Origem:** complexidade desnecessária

### Problema

`AppointmentFactory` cria sempre o mesmo objeto e utiliza condicionais apenas para configurar prioridade.

### Decisão de design

Avaliar se a Factory representa uma necessidade real ou se a criação pode ser simplificada.

### Critérios de aceitação

* a decisão entre manter ou remover a Factory é documentada;
* não existe abstração sem responsabilidade claramente definida;
* o comportamento de criação de consultas é preservado.

---

## Item 6 — Formalizar requisitos não funcionais

**Prioridade:** alta
**Origem:** requisitos

### Problema

Termos como “seguro”, “rápido” e “sempre disponível” não possuem critérios objetivos.

### Decisão de design

Definir critérios verificáveis para requisitos não funcionais.

### Critérios de aceitação

* requisitos possuem métricas ou condições verificáveis;
* requisitos de segurança, disponibilidade e desempenho possuem definição objetiva;
* decisões técnicas podem ser relacionadas aos requisitos correspondentes.

---

## Ordem recomendada

1. corrigir múltiplos observadores;
2. definir critérios para requisitos não funcionais;
3. desacoplar integrações do fluxo de exames;
4. revisar Strategy;
5. melhorar encapsulamento;
6. revisar Factory.
