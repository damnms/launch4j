#!/bin/sh
LAUNCH4J="$(dirname "$0")"/launch4j.jar
if [ -n "$JAVA_HOME" ]; then
  "$JAVA_HOME"/bin/java -jar "$LAUNCH4J" "$@"
else
  java -jar "$LAUNCH4J" "$@"
fi
