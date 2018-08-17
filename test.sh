#!/bin/bash

function log() {
    echo "$(date +%D-%H:%M:%S) - $1"    
}

function handle_error() {
    EXIT_CODE=$1
    if [ $EXIT_CODE -ne 0 ]
    then
        log "#### PROCESS FAILED WITH EXIT CODE $EXIT_CODE. SCRIPT WILL EXIT ####"
        log "Description: $2"
        log "### BEGIN PROCESS STDOUT ###"
        echo "$(cat test.log)"
        log "### END PROCESS STDOUT ###"
        
        pgrep -f eu.ggam.container | xargs kill -9 # Kill Any possibly running servers
        exit 1
    fi
}

####################################
# IMPL UNIT TESTS
####################################

log "Running Impl unit tests: start"
(cd impl; mvn clean test) > test.log 2>&1

handle_error $? "Error unit testing Impl"

log "Running Impl unit tests: SUCCESS"

####################################
# COMPILE INTEGRATION TESTS
####################################

log "Compiling integration tests: start"
(cd integrationtests; mvn clean install -DskipTests) >test.log 2>&1

handle_error $? "Error compiling integration tests"

log "Compiling integration tests: SUCCCESS"

####################################
# SERVLET INTEGRATION TESTS
####################################

log "Servlet integration tests: start"

SERVLET_HOME="$(pwd)/integrationtests/servlet"
SERVLET_LOG="$SERVLET_HOME/target/jre-dist2/logs/server.log"

log "Starting server"

CURRENT_DIR=$(pwd)
cd "$SERVLET_HOME/target/jre-dist2/bin/"
./launch >test.log 2>&1 &

handle_error $? "Error starting server for Servlet integration tests"
cd $CURRENT_DIR

log "Server started"

sleep 1
#tail -F $log | grep -q "Server is running on port 8282"

(cd $SERVLET_HOME; mvn test) >test.log 2>&1
handle_error $? "Error executing Servlet integration tests"

log "Killing server process $SERVLET_PROCESS"

pgrep -f eu.ggam.container | xargs kill -9
handle_error $? "Could not kill running server!"

log "Servlet integration tests: SUCCESS"

####################################
# IMPL INTEGRATION TESTS
####################################

log "Impl integration tests: start"

IMPL_HOME="$(pwd)/integrationtests/impl"
IMPL_LOG="$IMPL_HOME/target/jre-dist2/logs/server.log"

log "Starting server"

CURRENT_DIR=$(pwd)
cd "$IMPL_HOME/target/jre-dist2/bin/"
./launch >test.log 2>&1 &

handle_error $? "Error starting server for Impl integration tests"
cd $CURRENT_DIR

log "Server started"

sleep 5
#tail -F $log | grep -q "Server is running on port 8282"

(cd $IMPL_HOME; mvn test) >test.log 2>&1
handle_error $? "Error executing Impl integration tests"

log "Killing server process"

pgrep -f eu.ggam.container | xargs kill -9
handle_error $? "Could not kill running server!"

log "Impl integration tests: SUCCESS"

rm test.log
