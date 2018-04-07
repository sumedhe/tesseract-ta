#!/bin/bash

docker exec -it tesseract4-container sh ./scripts/build_deb_pkg.sh
mkdir ./pkg/
rm ./pkg/*
docker cp tesseract4-container:/home/pkg/ ./
echo "Check ./pkg directory"
