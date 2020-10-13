#!/bin/bash

for H in ch0?-* ch1[12]-*
do
    echo cp hibernate.properties $H/src/main/resources
    cp hibernate.properties $H/src/main/resources
done

#cp hibernate.properties-ch10 ch10-mysql/src/main/resources
#cp hibernate.properties      ch11-eclipse/src/main/resources
#cp hibernate.properties      ch12-maven-annotation/src/main/resources

# Windows file format
find . -depth -type f | xargs --verbose -L1 unix2dos --quiet
find . -depth -type f -name "*.sh" | xargs --verbose -L1 dos2unix --quiet

