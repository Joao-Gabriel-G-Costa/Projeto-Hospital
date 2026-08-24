# ADR-0002 — Separação da responsabilidade de notificações

## Status

Aceito.

## Contexto

A `HospitalApplicationService` coordenava processos hospitalares e também conhecia detalhes de canal e construção das mensagens enviadas aos pacientes, reduzindo sua coesão.

## Decisão

Os detalhes das notificações de consultas e exames serão delegados à `NotificationService`. A `HospitalApplicationService` continuará coordenando o fluxo, mas apenas solicitará a notificação correspondente.

## Alternativa descartada

Separar imediatamente consultas, exames e internações em serviços diferentes foi descartado neste momento, pois aumentaria significativamente a quantidade de alterações sem necessidade para resolver o problema escolhido.

## Consequências

A `HospitalApplicationService` passa a possuir menos conhecimento sobre comunicação com pacientes e a `NotificationService` concentra essa responsabilidade. Como consequência, surgem métodos de notificação específicos, criando uma dependência explícita entre os dois serviços.
