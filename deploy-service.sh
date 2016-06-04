#!/usr/bin/env bash

echo "deploying service"
mvn clean install -Dmaven.test.skip;

cd target;
tar -xvzf hope-service-rpc-deploy.tar.gz
cd hope-service-rpc-deploy;
sh ./bin/start_low_mem.sh;