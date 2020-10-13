#!/bin/bash

for H in ch0?-* ch1[12]-*
do
    pushd $H
    gradle cleanEclipse
    popd
done


