# Matriz de Decisão Arquitetural — MediConnect (Aula 06)

## Como foi utilizado

1. Foram escolhidas 3 alternativas arquiteturais aplicáveis (ou descartáveis) ao MediConnect.
2. Os critérios foram definidos a partir dos RNFs levantados na atividade principal (segurança, desempenho, manutenibilidade, disponibilidade/confiabilidade) mais dois critérios práticos do contexto do projeto (complexidade operacional e custo/esforço de migração).
3. Cada critério recebeu um peso (1 a 3).
4. Cada alternativa recebeu uma nota por critério (1 a 5).
5. Peso × Nota foi calculado célula a célula.
6. Os resultados de cada alternativa foram somados.
7. As justificativas de cada nota estão no arquivo `JUSTIFICATIVAS-NOTAS-preenchido.md`.

---

## Escala de pesos utilizada

| Peso | Significado |
|---:|---|
| 1 | Baixa importância para o projeto |
| 2 | Importância média |
| 3 | Alta importância |

## Escala de notas utilizada

| Nota | Significado |
|---:|---|
| 1 | Atende fracamente ao critério |
| 2 | Atende parcialmente |
| 3 | Atende de forma adequada |
| 4 | Atende bem |
| 5 | Atende muito bem |

---

## Alternativas analisadas

- **Alternativa A:** Manter a arquitetura atual do MediConnect — monolito em camadas simples, processo único, chamadas síncronas (Main → MediConnectFacade → HospitalApplicationService → Repositórios/Adapters).
- **Alternativa B:** Arquitetura orientada a eventos com message broker (fila/tópico) — alternativa considerada e não adotada no `ADR-0001.md`.
- **Alternativa C:** Microsserviços por domínio (agendamento/exames, laboratório, convênio), comunicando-se via API entre si.

---

## Matriz

| Critério | Peso | Alternativa A | P×N | Alternativa B | P×N | Alternativa C | P×N |
|---|---:|---:|---:|---:|---:|---:|---:|
| Desempenho | 1 | 5 | 5 | 3 | 3 | 2 | 2 |
| Segurança | 3 | 3 | 9 | 3 | 9 | 3 | 9 |
| Manutenibilidade | 2 | 3 | 6 | 3 | 6 | 2 | 4 |
| Disponibilidade/Confiabilidade | 2 | 2 | 4 | 4 | 8 | 3 | 6 |
| Complexidade operacional | 3 | 5 | 15 | 2 | 6 | 2 | 6 |
| Custo/esforço de migração | 2 | 5 | 10 | 2 | 4 | 2 | 4 |
| **TOTAL** | | | **49** | | **36** | | **31** |


---

## Exemplo do cálculo

```text
Peso × Nota = Resultado

Exemplo (Complexidade operacional, Alternativa A):
3 × 5 = 15
```

---

## Resultado

- **Total da Alternativa A (manter arquitetura atual):** 49
- **Total da Alternativa B (orientada a eventos):** 36
- **Total da Alternativa C (microsserviços):** 31

### Alternativa com maior pontuação

**Alternativa A — manter a arquitetura em camadas simples.**

### Observação

A maior pontuação não determinou sozinha a decisão. O grupo também considerou:

- **Custos:** migrar para B ou C exigiria reescrever o `HospitalPublisher`/observers (B) ou dividir o sistema em serviços independentes com deploys e contratos de API (C), sem que exista hoje uma demanda real de escala que justifique esse investimento dentro do prazo do semestre.
- **Riscos:** o maior risco atual (segurança dos dados do paciente em trânsito e nos logs, e o bug de observer único do `HospitalPublisher`) é um **problema de implementação**, corrigível dentro da própria Alternativa A, e não uma limitação intrínseca do estilo em camadas.
- **Complexidade:** B e C exigem infraestrutura (broker de mensagens, orquestração de serviços) que a equipe não possui hoje.
- **Contexto do projeto:** é um sistema acadêmico, com equipe pequena e prazo de semestre — não há requisito real de escala/carga que justifique migração.

A decisão final (manter a arquitetura atual com melhorias internas) está registrada e justificada na atividade principal (`Relatório de Análise e Decisão Arquitetural — Aula 06`, seção 8).
