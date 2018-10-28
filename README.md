[![SonarQube](https://sonarcloud.io/api/project_badges/quality_gate?project=eu.ggam:container)](https://sonarcloud.io/dashboard?id=eu.ggam%3Acontainer)
[![Build Status](https://travis-ci.org/ggam/java9-container.svg?branch=master)](https://travis-ci.org/ggam/java9-container)

# Java 9 ServletContainer

This is a (WIP) toy project HTTP 1.1 server and Servlet 2.5 Container implementation on top of the Java Module System.

*By no means is this intended for production use.*

## API JavaDoc

http://ggam.eu/java9-container/site/apidocs

## Running

Install required dependency: `git clone https://github.com/ggam/java9-container-jlink; cd java9-container-jlink; mvn clean install`

Compile: `mvn clean install -DskipTests`

Test: `./test.sh`

Run: `./examples/container-runnable/target/jre-dist/bin/launch`
