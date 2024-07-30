## Description
Market-Notes is a comprehensive application designed to manage and analyze stock market data. 
It provides functionalities for loading, storing, and processing stock company information and their historical data. 
The application aims to assist users in making informed decisions by providing detailed analysis and historical trends of stock prices.

### Architecture:
Market-Notes follows a multi-tier architecture. It can be broken down into the following components:
- frontend: created in Angular
- backend: multi-module spring application, tested experimental application design style
- keycloak
- postgres database

## Build steps

### preparing keycloak
```shell
mvn clean install -f build-resource/keycloak/rest-event-listener/pom.xml 
cp build-resource/keycloak/rest-event-listener/target/keycloak.jar  build-resource/keycloak/rest-event-listener.jar
```

### preparing java app
```shell
mvn clean install -f market-notes/pom.xml
```

### preparing angular app
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

## Use application
- Create docker containers and optionally run angular application and java app manually (e.g. using IDE)
- Create users on keycloak dashboard as admin (http://localhost:8090, u:admin, p:admin) in market-notes realm, assign them roles `user` and `admin`
- Load companies to operate on them, use [load-companies.http](build-resource/request/load-companies.http) and [companies](build-resource%2Fcompanies) directory
- Open application in browser http://localhost:4200

### TODO
- Admin panel for load companies from GUI
