#!/bin/bash

cd impl

mvn clean test

cd ..

cd integrationtests

mvn clean install -DskipTests

# Servlet Tests
cd servlet

home=$(pwd)
log="$home/target/jre-dist2/logs/server.log"

cd target/jre-dist2/bin/
./launch &

cd $home

sleep 1

#touch $log || exit
#echo 'Waiting for server to start...'
#echo $log
#tail -F $log | grep -q "Server is running on port 8282"
#echo 'Server started!'

mvn test
pgrep -f eu.ggam.container | xargs kill -9

sleep 1

# Server implementation Tests

cd ../impl

home=$(pwd)
log="$home/target/jre-dist2/logs/server.log"

cd target/jre-dist2/bin/
./launch &

cd $home

#touch $log || exit
#echo 'Waiting for server to start...'
#tail -F $log | grep -q "Server is running on port 8282"
#echo 'Server started!'

mvn test
pgrep -f eu.ggam.container | xargs kill -9
