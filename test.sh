#!/bin/bash

# Servlet Tests
./run.sh &
sleep 3 # Give some time for the server to start
cd integrationtests/servlet
mvn test
pgrep -f eu.ggam.container | xargs kill -9

# Server implementation Tests
cd ../impl

home=$(pwd)

cd target/jre-dist2/bin/
./launch &
sleep 3 # Give some time for the server to start

cd $home
mvn test
pgrep -f eu.ggam.container | xargs kill -9
