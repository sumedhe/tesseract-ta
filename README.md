# tessaract-ta

## Scripted steps (tested as a root `sudo su`):
1. `./scripts/1-pull-container.sh` - pull **tesseractshadow/tesseract4cmp** image from [Docker Hub](https://hub.docker.com/r/tesseractshadow/tesseract4cmp/) (automated build using dockerfile from this repository).
2. `./scripts/2-remove-container.sh` - (optional) remove **t4cmp** if it already exists and you want to start from begining (note, all compilation results stored inside container will be lost).
3. `./scripts/3-run-new-container.sh` - run the new **t4cmp** container.
4. `./scripts/4-update-src.sh` - update source code of Leptionica and Tesseract.
5. `./scripts/5-compile-src.sh` - compile Leptionica and Tesseract, it may take tens of minutes
6. `./scripts/6-test-ocr.sh` - do some OCR tests
7. `./scripts/7-build-pkg.sh` - (optional) build Leptionica and Tesseract packages and copy them outside **t4cmp** container

## Bulid docker container yourself

1. Clone this repository to your $T4_WORKSPACE
2. Execute `docker build -t tesseractshadow/tesseract4cmp $T4_WORKSPACE` (or `./dockerfile.build.sh`)

## If something went wrong
You can get into the container using SSH:
- `localhost:4022`,
- user: `root`,
- password: `root`
