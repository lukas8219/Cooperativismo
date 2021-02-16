# Desafio BackEnd - South System - Java Junior


## Introdução ao Projeto

doizsjfasojfja

## Tecnologias

### Framework = SpringBoot com SpringCloud, Eureka Server Netflix. - para trabalhar com Microserviços
### Banco de Dados = MongoDB

 ## Microserviços
 
 Minha escolha de arquitetura e Design Pattern foi a de Microserviços. Além dos 3 microserviços, existem mais dois, os quais são o Cliente e o Servidor Eureka. 
 
 Os 4 objetivos foram dividos em seus respectivos microserviços.
 
 * Cadastrar nova pauta = New Agenda Service.
 * Abrir votação = Open Vote Service
 * Votar = Vote Service
 * Contabilizar Votos = New Agenda Service.
 
 AgendaVote Client = Microserviço que une todos modulos.
 
### New Agenda Service 

Este serviço tem conexão direta com o Banco de Dados para criação de novas pautas, e consultas de resultados.
** Endpoints:
* POST : /open/ : que recebe um objeto Agenda, ou um Json contendo os campos "name" e "description". O mesmo gera seu UUID automaticamente. Retorna o objeto gerado.
* GET : / : retorna uma lista de todas pautas do banco de Dados. Utilizado para Debug.
* GET: /check/{agendaId} : retorna um objeto Result. que contém os campos "agenda" e "result". Result contém Approved, Refused ou Tied, como String.

### Open Vote Service

Este serviço contém conexão com o Banco de Dados para atualização do mesmo.

** Endpoints:
* PUT: /?id="agendaID"&time="" : Abre uma votação na pauta do ID determinado. a Key "Time" é opcional. (Em caso de não especificada, padrão 1 minuto). Este endpoint vai chamar exceções específicas em caso de Votação já Aberta, ou ID de Pauta invalido.

### Vote Service

Este serviço contém conexão com o Banco de Dados para atualização da Pauta.

** Endpoint
* POST : /?agenda="agendaId"?cpf="cpf"?decision="yes/no" : Contabiliza um voto. Este endpoint vai chamar as exceções específicas em caso de CPF Invalido, CPF duplicado, Votação Expirada, ID da Pauta inválido ou Pauta sem votação aberta.


### Cliente

O cliente não tem conexão com o Banco de Dados.

** Endpoints

* POST : /new/ : Conexão com /open/ de New Agenda Service
* GET : /result/{id} : Conexão com /check/{id} de New Agenda Service
* PUT : /open/?agenda={agendaId} : Conexão com Open Vote Service .
* POST : /vote/?agendaId="agendaId"&cpf="cpf"&vote="yes/no" : Conexão ocom Vote Service.


### Bibliotecas

Criei uma biblioteca generalista para conseguir facilitar a deserialização/serialização dos objetos entre as conexões dos microserviços. A mesma contém 4 pacotes:

* defaultObject = AgendaDefault e VoteDefault - Classes padrões para os objetos Agenda e Vote.
* defaultObject.utility = Result - objeto que armazena os resultado dos votos.
* exception = DuplicateVoteException, InvalidAgendaIdException, InvalidCpfException, OpenVoteException, VotationExpiredException, VotationNotOpenException, VotationOccuringException. São as classes de exceções específicas para cada tipo de erro.
* interfaces = Interfaces para facilitação de extensão entre os componentes/classes

### Testes Unitários

O serviço Cliente contém 5 testes:
* DefaultTest = Teste em condições normais, cria uma pauta, abre a pauta, vota(até o cpf ser validado pelo site) com 3 cpfs em YES, espera 1 minuto(visto que o tempo que não fora especificado) e testar caso a pauta tenha sido aprovada.

* DuplicateCPF : Testa caso exista algum CPF duplicado. Utiliza um ID existente no meu Banco de Dados local (**necessita ser atualizada em outros computadores**) e compara com CPF que já foi votado. **presente também no banco de dados**

* VotationExpired : Testa um tentativa de voto em votação expirada. utiliza um ID existente no Banco de Dados local cujo ja tenha expirado.

* VotationOccuring : Testa consultar resultado em votação que está acontecendo. 

### Documentação

Nas pastas dos microserviços, contém os respectivos JavaDoc

