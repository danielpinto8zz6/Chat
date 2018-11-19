#!/bin/sh

BASEDIR=$(dirname "$0")
cd $BASEDIR
java -jar Client/target/Client-1.0-SNAPSHOT-jar-with-dependencies.jar
