#!/usr/bin/env bash

OUTDIR="data"
#mkdir $OUTDIR

cd $OUTDIR

echo Downloading data...
../download-srtm-data.sh

echo create tiles: SRTM_NE_250m
../create-tiles.sh SRTM_NE_250m.tif 10 10 &&
rm -rf SRTM_NE_250m.tif

echo create tiles: SRTM_SE_250m
../create-tiles.sh SRTM_SE_250m.tif 10 10 &&
rm -rf SRTM_SE_250m.tif

echo create tiles: SRTM_W_250m
../create-tiles.sh SRTM_W_250m.tif 10 20 &&
rm -rf SRTM_W_250m.tif

echo removing unused data
rm -rf readme.txt TIFF-Files-here.md
