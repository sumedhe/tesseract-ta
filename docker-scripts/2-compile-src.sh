#!/bin/bash

docker exec -it tesseract4-container sh scripts/compile_leptonica.sh && \
docker exec -it tesseract4-container sh scripts/compile_tesseract.sh && \
docker exec -it tesseract4-container tesseract \-v
