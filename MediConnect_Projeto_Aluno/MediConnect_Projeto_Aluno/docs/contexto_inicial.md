# Contexto inicial — MediConnect

O MediConnect representa um sistema para apoio à gestão de um hospital inteligente. O sistema possui funcionalidades relacionadas a consultas, internações, exames, comunicação com pacientes e integração com serviços externos.

## Stakeholders

### Paciente

Utiliza os serviços oferecidos pelo hospital e recebe informações relacionadas a consultas, exames e demais atendimentos.

### Médico

Participa dos atendimentos e consultas registradas pelo sistema.

### Hospital

Responsável pela operação do MediConnect e pelos processos administrativos e clínicos representados no sistema.

### Recepção

**Hipótese:** pode utilizar o sistema para cadastro, consulta de informações e apoio ao agendamento de pacientes.

### Enfermagem

**Hipótese:** pode utilizar informações de internação e prioridade de atendimento.

### Auditoria

Possui interesse no registro e rastreabilidade das operações realizadas no sistema.

### Operadora de saúde / convênio

Sistema externo responsável por autorizar determinados procedimentos.

### Laboratório

Sistema externo que recebe solicitações de exames.

## Sistemas externos

O código atual demonstra integração com três recursos externos:

* serviço de laboratório;
* sistema de autorização de convênio;
* serviço de mensagens WhatsApp.

Esses sistemas estão representados pelas classes legadas `LabXClient`, `LegacyHealthPlanApi` e `WhatsappHospitalApi`.

## Fronteira do MediConnect

São responsabilidades internas do MediConnect:

* gerenciamento simplificado de pacientes;
* agendamento de consultas;
* solicitação de exames;
* registro de internações;
* controle de prioridade;
* coordenação de notificações.

Não são responsabilidades internas:

* processar efetivamente exames laboratoriais;
* decidir regras internas das operadoras de saúde;
* executar a infraestrutura real do WhatsApp;
* definir protocolos médicos ou clínicos.

Essas funcionalidades pertencem a sistemas ou organizações externas.

## Restrições identificadas

* dependência de serviços externos para determinadas operações;
* informações de pacientes precisam ser tratadas de maneira segura;
* indisponibilidade de parceiros externos pode afetar alguns fluxos;
* o sistema legado utiliza modelos simplificados e ainda possui pouca validação;
* requisitos de segurança e disponibilidade ainda não possuem critérios mensuráveis.

## Necessidades e possíveis conflitos

O paciente precisa de atendimento simples e comunicação rápida, enquanto o hospital precisa manter rastreabilidade e controle das operações.

Integrações com laboratório e convênio simplificam processos, porém criam dependências externas.

A necessidade de facilidade de manutenção pode entrar em conflito com decisões legadas que concentram responsabilidades em poucas classes.

## Hipóteses que precisam de validação

* recepcionistas terão acesso direto ao sistema;
* profissionais de enfermagem consultarão dados de internação;
* diferentes convênios poderão possuir regras distintas;
* diferentes laboratórios poderão ser utilizados;
* notificações poderão possuir preferências de canal por paciente;
* serão necessários níveis diferentes de autorização para profissionais.
