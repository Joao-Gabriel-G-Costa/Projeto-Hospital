# Aula 01 — Fundamentos e organização do MediConnect

## Diferença entre requisito, design, arquitetura e implementação

No MediConnect, os requisitos representam aquilo que o sistema precisa oferecer ao usuário ou ao hospital. Um exemplo é o requisito RF01, que determina que o sistema deve permitir o agendamento de consultas.

O design representa como as responsabilidades e componentes do sistema são organizados para atender esses requisitos. Um exemplo é a existência da classe `HospitalApplicationService`, responsável por coordenar operações como consultas, exames e internações.

A arquitetura representa uma visão mais ampla da estrutura do sistema e da comunicação entre seus componentes. O documento legado informa que o MediConnect utiliza arquitetura orientada a eventos, porém a implementação atual ainda executa principalmente em um único processo e utiliza chamadas síncronas.

A implementação corresponde ao código efetivamente desenvolvido. Exemplos são os métodos `schedule()`, `requestExam()` e `admit()` da classe `HospitalApplicationService`.

## Organização do repositório

O projeto está organizado principalmente nas seguintes áreas:

* `src/main/java`: código-fonte da aplicação;
* `src/test/java`: verificações e testes do projeto;
* `docs`: documentação técnica e arquitetural;
* `docs/adr`: registros de decisões arquiteturais;
* `atividades`: instruções das atividades de cada aula;
* `evidencias`: registros utilizados nas entregas;
* `scripts`: scripts auxiliares de compilação.

Essa organização será mantida durante o semestre, adicionando documentos específicos de cada aula dentro de `docs` e registrando decisões importantes em `docs/adr`.

## Papéis do grupo

O grupo trabalha de forma colaborativa. As responsabilidades incluem análise do código legado, desenvolvimento e refatoração, documentação das decisões, validação das alterações e organização das entregas no GitHub.

Os membros podem dividir as tarefas durante cada atividade, mas as decisões técnicas devem ser revisadas pelo grupo antes da entrega.

## Critério de aceitação

A atividade é considerada concluída quando as diferenças entre requisito, design, arquitetura e implementação estiverem identificadas e a organização utilizada para evolução do projeto estiver documentada.
