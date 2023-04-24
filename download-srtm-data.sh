#!/usr/bin/env bash

set -eu
wget https://srtm.csi.cgiar.org/wp-content/uploads/files/250m/SRTM_NE_250m_TIF.rar && \
wget https://srtm.csi.cgiar.org/wp-content/uploads/files/250m/SRTM_SE_250m_TIF.rar && \
wget https://srtm.csi.cgiar.org/wp-content/uploads/files/250m/SRTM_W_250m_TIF.rar && \
unar -f SRTM_NE_250m_TIF.rar && \
unar -f SRTM_SE_250m_TIF.rar && \
unar -f SRTM_W_250m_TIF.rar
mv SRTM_NE_250m_TIF/SRTM_NE_250m.tif .
mv SRTM_SE_250m_TIF/SRTM_SE_250m.tif .
mv SRTM_W_250m_TIF/SRTM_W_250m.tif .