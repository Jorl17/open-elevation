#!/usr/bin/env bash

wget -nc http://srtm.csi.cgiar.org/wp-content/uploads/files/250m/SRTM_NE_250m_TIF.rar &&
wget -nc http://srtm.csi.cgiar.org/wp-content/uploads/files/250m/SRTM_W_250m_TIF.rar && 
wget -nc http://srtm.csi.cgiar.org/wp-content/uploads/files/250m/SRTM_SE_250m_TIF.rar && 
unar -f SRTM_NE_250m_TIF.rar -o ../data -D && 
unar -f SRTM_SE_250m_TIF.rar -o ../data -D && 
unar -f SRTM_W_250m_TIF.rar -o ../data -D &&

echo
echo
echo *******************
echo 	Deleting rars
echo *******************
rm -rf SRTM_W_250m_TIF.rar SRTM_SE_250m_TIF.rar SRTM_NE_250m_TIF.rar 
