#!/usr/bin/env zsh

# don't throw errors when file globs don't match anything
setopt NULL_GLOB

rm -rf  */{.gradle,.settings,bin,build,.vscode}
rm -f */{.project,.classpath}

rm -rf */gradle
rm -f */{gradlew,gradlew.bat,settings.gradle}

