## Sistema de chat utilizando multicast para disciplina de SDI

  Integrantes: Gabriel Junkes, Leonardo Sell, Lucas Tascheck
  Github repo: https://github.com/GabrielFJunkes/SDI-Chat-Multicast 
## Como rodar
Assumindo ambiente Linux.

Digite `make` para compilar o projeto

`java server.Server {PORTA}` para rodar o servidor, sendo a porta default 2222.

`java client.Client {IP} {PORTA}` para rodar o cliente.

### Obs
- A senha padrão do servidor é `senha`.
- O ip de multicast é aleatório, gerado na iniciação do server.
