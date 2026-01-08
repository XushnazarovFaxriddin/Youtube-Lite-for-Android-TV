#!/usr/bin/env sh

# Minimal Gradle wrapper script (POSIX)
DIR="$(cd "$(dirname "$0")" && pwd)"
JAVA_CMD="java"

if [ -n "$JAVA_HOME" ] && [ -x "$JAVA_HOME/bin/java" ]; then
  JAVA_CMD="$JAVA_HOME/bin/java"
fi

exec "$JAVA_CMD" -classpath "$DIR/gradle/wrapper/gradle-wrapper.jar" org.gradle.wrapper.GradleWrapperMain "$@"
