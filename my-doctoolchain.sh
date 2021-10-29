#!/usr/bin/env bash
docker run -it --rm --entrypoint /bin/bash -v ${PWD}:/project builder-alpine-doctoolchain:2.0.2 \
-c "doctoolchain . $1 $2 $3 $4 $5 $6 $7 $8 $9 && exit"
