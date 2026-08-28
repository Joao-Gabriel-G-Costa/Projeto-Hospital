# Evidência de verificação — Aula 05

A atividade da Aula 05 documenta os componentes, conectores e a configuração já existentes no MediConnect. Não foi necessária alteração de comportamento no código-fonte.

## Verificação realizada

O código foi compilado com Java utilizando compatibilidade com Java 17 (`javac --release 17`). Em seguida foram executadas a classe principal e a classe de diagnóstico.

### Aplicação principal

```text
EMAIL ana@example.com: Consulta A1 agendada em 2026-08-20T19:00
AUDIT A1 SCHEDULED
Agendada: true status=SCHEDULED
WA 62999999999 => Exame E1 = SENT_TO_LAB
AUDIT E1 SENT_TO_LAB
Exame autorizado: true status=SENT_TO_LAB
```

### DiagnosticChecks

```text
AUDIT X1 TEST
Score emergency: 100
```

## Resultado

A compilação foi concluída com sucesso e os fluxos executados terminaram sem erro de execução.
