#!/usr/bin/env bash

#
# Builds and starts the application. This script can be executed from any directory.
#

echo "$(date) Building and starting facepalm application..."

(cd "${BASH_SOURCE%/*}" && ./gradlew clean bootRun)
