# Desafio BackEnd - South System - Java Junior

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
**Endpoints**
* POST : /open/ : que recebe um objeto Agenda, ou um Json contendo os campos "name" e "description". O mesmo gera seu UUID automaticamente. Retorna o objeto gerado.
* GET : / : retorna uma lista de todas pautas do banco de Dados. Utilizado para Debug.
* GET: /check/{agendaId} : retorna um objeto Result. que contém os campos "agenda" e "result". Result contém Approved, Refused ou Tied, como String.

### Open Vote Service

Este serviço contém conexão com o Banco de Dados para atualização do mesmo.

**Endpoints**
* PUT: /?id="agendaID"&time="" : Abre uma votação na pauta do ID determinado. a Key "Time" é opcional. (Em caso de não especificada, padrão 1 minuto). Este endpoint vai chamar exceções específicas em caso de Votação já Aberta, ou ID de Pauta invalido.

### Vote Service

Este serviço contém conexão com o Banco de Dados para atualização da Pauta.

**Endpoints**
* POST : /?agenda="agendaId"?cpf="cpf"?decision="yes/no" : Contabiliza um voto. Este endpoint vai chamar as exceções específicas em caso de CPF Invalido, CPF duplicado, Votação Expirada, ID da Pauta inválido ou Pauta sem votação aberta.


### Cliente

O cliente não tem conexão com o Banco de Dados.

**Endpoints**

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

O serviço Cliente contém 1 teste que chama outros 4 testes, na ordem cronológica dos possiveis erros:
* DefaultTest =  Este teste cria uma nova pauta, abre a mesma. E então chama "Already Open". Seguindo, ela abre uma votação, (insiste até conseguir validar o CPF) e então chama "Duplicate CPF". Após este teste, o mesmo chama "Votation Occuring".(pois ainda está dentro do 1 minuto padrão). Após, espera 60 segundos para votação terminar, e testa se a pauta foi aprovada.(3 votos em YES). Para finalizar, chama Votation Expired.

* DuplicateCPF : Testa caso exista algum CPF duplicado.

* VotationExpired : Testa um tentativa de voto em votação expirada.

* VotationOccuring : Testa consultar resultado em votação que está acontecendo.

* Already Open : Teste se consegue abrir a pauta, mesmo que ela já tenha sido aberta.

### Documentação

Nas pastas dos microserviços, contém os respectivos JavaDoc

### Tarefas Bônus utilizadas:

Selecionei apenas a Tarefa Bônus 1 de Integração com Sistemas Externos. O Código está na classe em VoteService/src/main/java/AgendaVote/voteservice/models/CPF.java

# Obrigado South System 
