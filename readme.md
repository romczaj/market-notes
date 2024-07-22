### build keycloak
```shell
mvn clean install -f build-resource/keycloak/rest-event-listener/pom.xml 
cp build-resource/keycloak/rest-event-listener/target/keycloak.jar  build-resource/keycloak/rest-event-listener.jar
```

### build java app
```shell
mvn clean install -f market-notes/pom.xml
```

### build angular app
```shell
cd frontend && npm i && ng build
```


### environment for local development - db and keycloak containerized
```shell
docker-compose build --no-cache
docker-compose up -d
```

### full environment - everything is containerized (db, keycloak, java app, angular app)
```shell
docker-compose -f docker-compose-extended.yml build --no-cache
docker-compose -f docker-compose-extended.yml up -d
```
This option requires adding entry  `127.0.0.1 market-notes-keycloak` in `/etc/hosts/` 

### use application
- create docker containers
- create user on keycloak dashboard as admin (http://localhost:8090, u:admin, p:admin) in market-notes realm
- load companies to operate on them, use [load-companies.http](build-resource/request/load-companies.http) and [companies](build-resource%2Fcompanies) directory
- open application in browser http://localhost:4200


