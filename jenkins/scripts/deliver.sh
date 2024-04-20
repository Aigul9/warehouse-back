#!/usr/bin/env bash

set -x
mvn jar:jar install:install help:evaluate -Dexpression=project.name
set +x

set -x
NAME=`mvn -q -DforceStdout help:evaluate -Dexpression=project.name`
set +x

set -x
VERSION=`mvn -q -DforceStdout help:evaluate -Dexpression=project.version`
set +x

set -x
java -jar target/${NAME}-${VERSION}.jar