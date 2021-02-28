# wallhaven-spring






#### Description:

#### Frontend 

This repository is the backend part of our <a href="https://github.com/maryn1421/wallhaven">wallhaven</a> project. <br>
The frontend is a react webapp.


For this project we have used the <a href="https://spring.io/projects/spring-boot">Spring Boot framework</a> and our used java version is 11. <br>

### Database

In our second sprint we used pure JDBC, in the third sprint we switched to JPA ORM. For database we used postgreSQL  

#### Authorization / security

Our API is authorized by the spring security jwt token based authentication.

##### About jwt token based authentication

In the JWT auth process, the front end (client) firstly sends some credentials to authenticate itself (email and password in our case).

The server  then checks those credentials, and if they are valid, it generates a JWT and returns it.

After this step client has to provide this token in the request’s Authorization header in the “Bearer TOKEN” form. The back end will check the validity of this token and authorize or reject requests. The token may also store user roles and authorize the requests based on the given authorities.
