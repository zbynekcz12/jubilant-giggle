#!/bin/bash

# Install dependencies
./gradlew build

# Run the application
adb install -r app/build/outputs/apk/debug/app-debug.apk
adb shell am start -n com.example.fingerprintapp/.MainActivity