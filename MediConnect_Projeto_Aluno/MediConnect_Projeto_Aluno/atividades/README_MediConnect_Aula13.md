# MediConnect — Aula 13

## Tema
Integrações, implantação, Adapter e Facade

## Situação do projeto
O MediConnect contém decisões e artefatos legados que podem estar incompletos, inconsistentes ou inadequados ao conteúdo desta aula. O aluno deve analisar o estado atual antes de modificar.

## O que o aluno deve fazer
1. analisar as integrações de convênio e laboratório e explicitar contratos;
2. identificar problemas nas implementações existentes de `HealthPlanAdapter`, `LabAdapter` e `MediConnectFacade`;
3. aplicar Adapter e Facade somente onde houver problema pertinente, registrando ADR e UML/Mermaid de cada padrão;
4. propor topologia de implantação e considerar falhas, segurança e desempenho das integrações.

## Dicas
- Adapter deve proteger o domínio das peculiaridades externas.
- Facade não deve virar simples porta para expor todos os subsistemas internos.

## Evidências mínimas da entrega
- Issue identificada como `Aula 13`.
- Branch sugerida: `aula-13`.
- Commits com mensagens que expliquem mudanças relevantes.
- README/Markdown da entrega contendo **problema observado, alteração realizada e critério de aceitação**.
- PR ou registro equivalente permitindo revisão do grupo.

## Prazo operacional
O trabalho deve começar em sala. O limite de conclusão é o início da aula seguinte, salvo orientação diferente do professor.
