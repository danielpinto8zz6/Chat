#!/bin/sh

BASEDIR=$(dirname "$0")
cd $BASEDIR
java -jar Server/target/Server-1.0-SNAPSHOT.jar
