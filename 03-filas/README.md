## Sistema de chat utilizando multicast para disciplina de SDI

  Integrantes: Gabriel Junkes, Leonardo Sell, Lucas Tascheck
  Github repo: https://github.com/GabrielFJunkes/SDI-Trabalhos 
## Como rodar
Assumindo ambiente Linux, com rabbitmq client em uma variável de ambiente $CP.

Digite `make` para compilar o projeto

`java -cp $CP server.RunServer {num}` para rodar server, com {num} sendo o número de servers que deseja criar.

`java -cp $CP client.Client {num}` para rodar o cliente, com {num} sendo o número de mensagens que deseja criar.

Os logs serão adicionados em ./logs
