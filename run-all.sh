#!/usr/bin/env zsh

#set -xv

for H in [0-9][0-9]*-*
do
    pushd $H/
        # gradle cleanEclipse
        # gradle clean
        # gradle eclipse
        gradle wrapper
        #./gradlew -version
    popd
done

