#!/usr/bin/env bash
# Original Source: https://github.com/mapbox/gdal-polygonize-test
set -eu

raster=$1
xtiles=$2
ytiles=$3

# get raster bounds
ul=($(gdalinfo $raster | grep '^Upper Left' | sed -e 's/[a-zA-Z ]*(//' -e 's/).*//' -e 's/,/ /'))
lr=($(gdalinfo $raster | grep '^Lower Right' | sed -e 's/[a-zA-Z ]*(//' -e 's/).*//' -e 's/,/ /'))

xmin=${ul[0]}
xsize=$(echo "${lr[0]} - $xmin" | bc)
ysize=$(echo "${ul[1]} - ${lr[1]}" | bc)

xdif=$(echo "$xsize/$xtiles" | bc -l)

for x in $(eval echo {0..$(($xtiles-1))}); do
    xmax=$(echo "$xmin + $xdif" | bc)
    ymax=${ul[1]}
    ydif=$(echo "$ysize/$ytiles" | bc -l)

    for y in $(eval echo {0..$((ytiles-1))}); do
        ymin=$(echo "$ymax - $ydif" | bc)

        # Create chunk of source raster
        gdal_translate -q \
            -projwin $xmin $ymax $xmax $ymin \
            -of GTiff \
            $raster ${raster%.tif}_${x}_${y}.tif

        ymax=$ymin
    done
    xmin=$xmax
done
