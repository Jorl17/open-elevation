#!/usr/bin/env bash

set -eu

wget http://gisweb.ciat.cgiar.org/TRMM/SRTM_Resampled_250m/SRTM_NE_250m_TIF.rar && \
wget http://gisweb.ciat.cgiar.org/TRMM/SRTM_Resampled_250m/SRTM_SE_250m_TIF.rar && \
wget http://gisweb.ciat.cgiar.org/TRMM/SRTM_Resampled_250m/SRTM_W_250m_TIF.rar && \
unar -f SRTM_NE_250m_TIF.rar && \
unar -f SRTM_SE_250m_TIF.rar && \
unar -f SRTM_W_250m_TIF.rar
