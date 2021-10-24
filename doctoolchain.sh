#!/usr/bin/env bash
docker run --rm -it --entrypoint /bin/bash -v ${PWD}:/project rdmueller/doctoolchain:v2.0.2 \
-c "doctoolchain . $1 $2 $3 $4 $5 $6 $7 $8 $9 -PinputPath=src/docs -PmainConfigFile=Config.groovy && exit"
