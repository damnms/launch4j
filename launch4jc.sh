#!/bin/sh
LAUNCH4J="$(dirname "$0")"/launch4j.jar
if [ -n "$JAVA_HOME" ]; then
  "$JAVA_HOME"/bin/java -Djava.awt.headless=true -jar "$LAUNCH4J" "$@"
else
  java -Djava.awt.headless=true -jar "$LAUNCH4J" "$@"
fi

