# Matriz de Decisão Arquitetural — MediConnect (Aula 06)

## 1. Como a matriz foi utilizada

Para comparar as alternativas arquiteturais do MediConnect, foram utilizados critérios relacionados aos requisitos não funcionais e ao contexto atual do projeto.

O processo utilizado foi:

1. definição de três alternativas arquiteturais;
2. escolha dos critérios de avaliação;
3. definição do peso de cada critério;
4. atribuição de notas de 1 a 5 para cada alternativa;
5. cálculo de `Peso × Nota`;
6. soma dos resultados;
7. análise dos custos, riscos e trade-offs antes da decisão final.

As justificativas das notas estão registradas em:

`docs/Entrega_Aula6/JUSTIFICATIVAS-NOTAS.md`

---

## 2. Escala de pesos

| Peso | Significado |
|---:|---|
| 1 | Baixa importância para o projeto |
| 2 | Importância média |
| 3 | Alta importância |

---

## 3. Escala de notas

| Nota | Significado |
|---:|---|
| 1 | Atende fracamente ao critério |
| 2 | Atende parcialmente ao critério |
| 3 | Atende de forma adequada |
| 4 | Atende bem ao critério |
| 5 | Atende muito bem ao critério |

---

## 4. Alternativas analisadas

### Alternativa A — Manter a arquitetura atual

Manter o MediConnect como uma aplicação em camadas simples, executada em processo único e com chamadas síncronas entre seus componentes.

Os problemas identificados seriam tratados através de melhorias internas, sem alteração do estilo arquitetural principal.

### Alternativa B — Arquitetura orientada a eventos

Introduzir comunicação assíncrona utilizando um message broker, filas ou tópicos para publicação e consumo de eventos.

Essa alternativa poderia aumentar o desacoplamento e oferecer mecanismos de reprocessamento, mas exigiria nova infraestrutura e maior complexidade operacional.

### Alternativa C — Microsserviços

Separar partes do sistema em serviços independentes, por exemplo:

- agendamento e consultas;
- exames;
- laboratório;
- convênio.

Os serviços passariam a possuir comunicação e implantação independentes.

Essa alternativa oferece maior isolamento entre domínios, porém aumenta significativamente a complexidade da solução.

---

## 5. Critérios utilizados

Foram considerados os seguintes critérios:

- desempenho;
- segurança;
- manutenibilidade;
- disponibilidade/confiabilidade;
- complexidade operacional;
- custo/esforço de migração.

Os pesos foram definidos considerando o contexto atual do MediConnect e o impacto de cada critério na evolução do projeto.

---

## 6. Matriz de decisão

| Critério | Peso | Alternativa A | P×N | Alternativa B | P×N | Alternativa C | P×N |
|---|---:|---:|---:|---:|---:|---:|---:|
| Desempenho | 1 | 5 | 5 | 3 | 3 | 2 | 2 |
| Segurança | 3 | 3 | 9 | 3 | 9 | 3 | 9 |
| Manutenibilidade | 2 | 3 | 6 | 3 | 6 | 2 | 4 |
| Disponibilidade/Confiabilidade | 2 | 2 | 4 | 4 | 8 | 3 | 6 |
| Complexidade operacional | 3 | 5 | 15 | 2 | 6 | 2 | 6 |
| Custo/esforço de migração | 2 | 5 | 10 | 2 | 4 | 2 | 4 |
| **TOTAL** |  |  | **49** |  | **36** |  | **31** |

---

## 7. Exemplo de cálculo

O valor de cada célula ponderada foi calculado utilizando:

```text
Peso × Nota = Resultado
```

Exemplo para o critério **Complexidade operacional** da Alternativa A:

```text
Peso = 3
Nota = 5

3 × 5 = 15
```

---

## 8. Resultado da matriz

Os totais obtidos foram:

| Alternativa | Total |
|---|---:|
| A — Manter arquitetura atual | **49** |
| B — Arquitetura orientada a eventos | **36** |
| C — Microsserviços | **31** |

A Alternativa A recebeu a maior pontuação na matriz.

Entretanto, a pontuação não foi utilizada isoladamente para definir a decisão arquitetural.

Também foram considerados o contexto do projeto, os custos, os riscos e os trade-offs envolvidos em cada alternativa.

---

## 9. Análise do resultado

### Alternativa A

A arquitetura atual apresenta como principais vantagens:

- menor complexidade operacional;
- menor esforço de migração;
- estrutura já conhecida pela equipe;
- ausência de necessidade de infraestrutura adicional.

Os principais problemas identificados, como o comportamento do `HospitalPublisher`, podem ser tratados internamente sem exigir uma mudança completa de arquitetura.

### Alternativa B

A arquitetura orientada a eventos apresenta vantagens principalmente relacionadas a:

- desacoplamento;
- confiabilidade na entrega de eventos;
- possibilidade de reprocessamento;
- comunicação assíncrona.

Entretanto, exigiria:

- infraestrutura de mensageria;
- configuração e manutenção de um broker;
- mudanças importantes na comunicação existente;
- maior conhecimento operacional da equipe.

### Alternativa C

A divisão em microsserviços poderia proporcionar:

- maior isolamento entre os domínios;
- evolução independente de serviços;
- possibilidade de escalabilidade individual.

Por outro lado, exigiria:

- separação do sistema atual;
- criação de contratos entre serviços;
- comunicação pela rede;
- múltiplos processos e deploys;
- maior complexidade de monitoramento;
- maior esforço de desenvolvimento.

---

## 10. Contexto considerado

A decisão também considerou que o MediConnect:

- é um projeto acadêmico;
- possui uma equipe pequena;
- é desenvolvido durante um semestre;
- utiliza atualmente um único projeto Maven;
- não possui infraestrutura distribuída;
- não possui requisito comprovado de alta escala;
- possui problemas que ainda podem ser solucionados dentro da estrutura atual.

---

## 11. Decisão

Com base na matriz e na análise dos trade-offs, foi decidido manter a arquitetura atual em camadas simples e realizar melhorias internas nos pontos identificados.

Essa decisão está detalhada em:

`docs/Entrega_Aula6/README-Aula06.md`

e relacionada à decisão arquitetural registrada em:

`docs/adr/ADR-0001.md`

A arquitetura poderá ser reavaliada futuramente caso os requisitos ou o contexto do MediConnect mudem.