# Aula 02 — Processo de Design, Requisitos e Plano de Trabalho

## 1. Processo de design adotado

Para a evolução do MediConnect, o grupo seguirá um processo de design incremental, permitindo analisar o sistema existente antes de realizar alterações no código.

O processo será dividido nas seguintes etapas:

1. **Compreender:** analisar o problema, o contexto do hospital, os stakeholders e o código existente.
2. **Levantar requisitos:** identificar as necessidades funcionais e não funcionais do sistema.
3. **Propor:** discutir possíveis soluções para os problemas encontrados.
4. **Modelar:** representar as decisões através de documentação, diagramas e organização das responsabilidades do sistema.
5. **Implementar:** realizar as alterações necessárias no código.
6. **Validar:** verificar se a implementação continua atendendo aos requisitos e se não foram introduzidos novos problemas.
7. **Evoluir:** utilizar os resultados das validações para realizar novas melhorias no projeto.

Esse processo será repetido durante a evolução do MediConnect ao longo do semestre.

---

## 2. Requisitos iniciais

Os requisitos iniciais levantados para o MediConnect estão registrados no arquivo:

`docs/requisitos_iniciais.md`

### Requisitos funcionais

- **RF01:** O sistema deve permitir marcar consultas.
- **RF02:** O sistema deve permitir solicitar exames.
- **RF03:** O sistema deve registrar internações.
- **RF04:** O sistema deve avisar o paciente quando necessário.

### Requisitos não funcionais

- **RNF01:** O sistema deve tratar as informações dos pacientes de maneira segura.
- **RNF02:** O sistema deve responder às operações sem atrasos que prejudiquem sua utilização.
- **RNF03:** O sistema deve possuir disponibilidade adequada para os serviços hospitalares.

---

## 3. Priorização inicial

Os requisitos foram classificados inicialmente de acordo com sua importância para o funcionamento do sistema.

| Requisito | Prioridade | Justificativa |
|---|---|---|
| RF01 — Marcar consultas | Alta | Representa uma das operações principais do sistema hospitalar. |
| RF02 — Solicitar exames | Alta | Faz parte do fluxo de atendimento e depende da integração com laboratórios. |
| RF03 — Registrar internações | Alta | É uma operação importante para o controle de pacientes no hospital. |
| RF04 — Avisar o paciente | Média | Melhora a comunicação, mas depende das operações principais. |
| RNF01 — Segurança | Alta | O sistema trabalha com informações de pacientes. |
| RNF02 — Desempenho | Média | O sistema precisa responder adequadamente durante sua utilização. |
| RNF03 — Disponibilidade | Alta | Serviços hospitalares precisam estar acessíveis quando necessários. |

A priorização poderá ser revista conforme novos requisitos e problemas forem identificados.

---

## 4. Backlog inicial

A partir do contexto e dos requisitos levantados, foi definido o seguinte backlog inicial:

| Item | Atividade | Prioridade | Status inicial |
|---|---|---|---|
| B01 | Analisar a estrutura e o código legado do MediConnect | Alta | Concluído |
| B02 | Identificar os principais fluxos do sistema | Alta | Concluído |
| B03 | Identificar problemas de responsabilidade e organização do código | Alta | Pendente |
| B04 | Avaliar as integrações com laboratório e convênio | Alta | Pendente |
| B05 | Avaliar o funcionamento das notificações | Média | Pendente |
| B06 | Melhorar a organização e manutenibilidade do código | Alta | Pendente |
| B07 | Criar testes para validar os principais comportamentos | Alta | Pendente |
| B08 | Atualizar a documentação conforme a evolução do projeto | Média | Em andamento |

O backlog representa uma visão inicial do projeto e poderá receber novos itens ao longo das próximas aulas.

---

## 5. Plano de trabalho

O grupo adotará o seguinte fluxo para realizar as atividades do projeto:

- [x] Compreender o contexto inicial do MediConnect.
- [x] Identificar os stakeholders e sistemas externos.
- [x] Levantar os requisitos iniciais.
- [x] Definir uma priorização inicial.
- [x] Criar um backlog inicial.
- [ ] Analisar tecnicamente o código existente.
- [ ] Identificar problemas e oportunidades de melhoria.
- [ ] Refatorar partes do sistema quando necessário.
- [ ] Criar testes para validar as alterações.
- [ ] Registrar decisões de design e arquitetura.
- [ ] Manter a documentação atualizada.

## 6. Organização do trabalho

As alterações serão realizadas utilizando o Git e o GitHub. O grupo utilizará branches para desenvolver e revisar as mudanças antes de integrá-las à versão principal do projeto.

Cada atividade realizada durante o semestre deverá deixar evidências no histórico do repositório através de commits e da documentação correspondente.