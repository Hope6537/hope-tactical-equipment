#!/usr/bin/env bash
mvn clean install -Dmaven.test.skip;
cd target;
tar -xvzf hope-service-rpc-deploy.tar.gz;
cd ..;
cd hope-service-restful-client;
mvn clean install -Dmaven.test.skip;