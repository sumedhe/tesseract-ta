#!/bin/bash

docker run -d -p 4022:22 --name t4cmp -v /Users/ivantha:/Users/ivantha tesseractshadow/tesseract4cmp
docker ps