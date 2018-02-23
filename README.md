jpa-auditing-spring-data-envers-oauth2

Example to integrate Auditing in Spring Data JPA with Hibernate Envers and oAuth2 to enable auditing and version control of persistent classes

Projeto para entendimento de metodolodias de auditoria e historico em microserviços.

Abordagem Historico com Envers - sincrono


Documentação de referência:
http://hibernate.org/orm/envers/
http://docs.jboss.org/hibernate/orm/current/quickstart/html_single/#tutorial_envers
http://docs.jboss.org/hibernate/orm/current/userguide/html_single/Hibernate_User_Guide.html#envers
https://docs.spring.io/spring-data/jpa/docs/1.7.0.DATAJPA-580-SNAPSHOT/reference/html/auditing.html
https://docs.jboss.org/hibernate/orm/4.0/hem/en-US/html/listeners.html
 
 
Resumo:
 
O projeto Envers tem como objetivo habilitar a auditoria / historico de entidades. Interessante entender os listeners para os callback de comandos DML(inserção, alteração, deleção): @PostPersist, @PostUpdate, @PostRemove, etc..
 
Quando adicionamos a anotation @Audited na nossa entidade, o envers já está fazendo a auditoria da nossa entidade usando a classe DefaultRevisionEntity para salvar as revisões da alteração além de salvar uma tabela espelho (por default, mesma tabela acrescentando _AUD). Esta tabela espelho é gerada com as colunas da nossa entidade, ou apenas os atributos que possui a Audited e mais alguns campos que identifica o id da DefaultRevisionEntity e qual a ação efetuada (DML). É possível sobrescrever essa entidade default (DefaultRevisionEntity) criando uma Entity e adicionando a anotação @RevisionEntity a qual permite também alterarmos a classe Listener que faz a manipulação da nossa entidade RevisionListener. Desse modo é possível definir uma classe de revisão personalizada, genérica, ou até mesmo especifica para uma determinada entidade.
 
Spring Data fornece uma implementação a informação do usuário da requisição como o usuário de criação/alteração da entidade durante o evento @PrePersist Durante esse evento também é possível definir a data criação/alteração. Para isso, nossa entidade precisa possuir os devidos campos anotados: @LastModifiedDate, @LastModifiedBy, @CreatedBy, @CreatedDate.
 
Default o usuário do contexto do Spring é o username, por isso é preciso sobrescrever o bean auditorProvider para disponibilizar corretamente o Id.
 
ex:
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class PersistenceConfig {
@Bean AuditorAware auditorProvider() {
return new AuditorAwareImpl();
}
}
 
public class AuditorAwareImpl implements AuditorAware {
@Override public Long getCurrentAuditor() {
Authentication auth = SecurityContextHolder.getContext().getAuthentication();
return ((UserDetail) auth.getPrincipal()).getId();
}
}
 
**** Por default o Principal que o JWT disponibiliza é o username, foi preciso sobrescrever esse passo no uaa
 
Vantagens dessa abordagem:
No dia a dia, um histórico padrão será resolvido com uma @Anotação na entidade.
A manutenção da entidade reflete diretamente no histórico.
Desvantagem:
Customização é mais difícil, pois é preciso entender o funcionamento do framework.
Concorrência ao acesso ao banco, pois cada manipulação gera um evento síncrono para persistir a informação do histórico.

 
Segundo momento:

Abordagem Fila - assincrono.


Utilizçaão dos callback de comandos DML para delegar o historico em uma mensageria (kafka, rabbitmq, etc). 

Criar uma anotação que implemente um listener para uma entidade informando por exemplo nome da fila, uma classe de configuração, ou até mesmo uma classe de implementação. Desse modo, o processo de persistir histórico se torna assincrono, podendo ser realizado por outro microserviço para persistir o historico em outro contexto, ou até mesmo pelo proprio microserviço com uma solulão de scheduling.

Vantagens dessa abordagem:

- Ganho de performance evitando a concorrencia ao banco principalmente em processos massivos.
- Facil manipular o serviço de persistir o histórico, tornando a customização mais facil.

Desvantagem:
- Necessidade de manipulação da fila e criar toda serviço de persistencia e mapeamentos das tabelas de historico.


**** Outh2-usuario-contexto-informacoes-adicionais *** 

Na configuração de autentificação do server AuthorizationServerConfig alteramos o TokenStore instanciando o JwtTokenStore passando o JwtAccessTokenConverter que possui as informações de SigningKey que é pertinente para a validação do Token. Dele também nos interessa o tokenConverter que é uma interface AccessTokenConverter que assume o DefaultAccessTokenConverter, e este é responsável por fazer a leitura do token e extrair o OAuth2AccessToken (extractAccessToken) e OAuth2Authentication (extractAuthentication).

new DefaultAccessTokenConverter() -- possui as inforamções padroes para ler o token JWT e extrair as informações do usuario

Logo é possivel utilizar o padrao decorator, disponibilizando o AccessTokenConverter adicionando na funcionalidade default o fluxo necessário para preencher no OAuth2Authentication os detalhes do token presentes no OAuth2AccessToken e que não é repassado para o contexto, disponibilizando as mesma no atributo details(do OAuth2Authentication) que armazena um Object.

Podemos também alterar dentro do converter DefaultAccessTokenConverter, como o mesmo recupera a inforamação do usuario, podemos disponibilizar uma implementação da interface UserAuthenticationConverter userTokenConverter sobrescrevendo a DefaultUserAuthenticationConverter.

Este é o ponto que define o Principal no contexto SecurityContextHolder, por isso, a alteração mais pontual seria ensinar ele a reciperar demais informações do usuario do token. Nese converter, é possibel identificar que, caso exista uma implementação de um serviço UserDetailsService ele recupera por meio do username, o UserDetail. 


class DefaultUSerAuthCOnverter public Authentication extractAuthentication(Map<String, ?> map) { metodo que recupera e seta o "principal" que é utilizado no contexto do spring se o userDetailsService for injetado é efetuado a consulta do usuario pelo username e, colocado no contexto principal, caso contrario assume como principal o username.. como ja temos a informação do usuario adicionado no acess_token basta fazer um decorator deste converter. }
