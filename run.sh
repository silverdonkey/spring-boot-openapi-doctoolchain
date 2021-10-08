#!/bin/bash

echo "JAVA_HOME=$JAVA_HOME"
echo "JAVA_OPTIONS=$JAVA_OPTIONS"

exec /usr/bin/java $JAVA_OPTIONS -jar service.jar --spring.profiles.active=$1