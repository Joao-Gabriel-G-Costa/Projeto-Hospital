# Arquitetura inicial
Um documento antigo afirma que o MediConnect adota **arquitetura orientada a eventos**, porque hospitais possuem muitos acontecimentos simultâneos.

A implementação atual executa em um único processo, faz chamadas síncronas diretas e não documenta eventos, filas, contratos ou fronteiras arquiteturais.
