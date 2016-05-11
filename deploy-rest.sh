#!/usr/bin/env bash

echo "deploying restapi"
mvn clean install -Dmaven.test.skip;
cd hope-service-restful-client;
mvn clean install -Dmaven.test.skip;
nohup java -jar target/hope-service-restful-client-1.3.2.RELEASE.jar &

tail -f nohup.out;