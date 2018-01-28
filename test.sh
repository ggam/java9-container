#!/bin/bash
./run.sh &
sleep 3 # Give some time for the server to start
cd integrationtests
mvn test
pgrep -f eu.ggam.container | xargs kill -9
