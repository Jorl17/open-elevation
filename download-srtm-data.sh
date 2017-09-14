#!/usr/bin/env bash

wget http://gisweb.ciat.cgiar.org/TRMM/SRTM_Resampled_250m/SRTM_NE_250m_TIF.rar && \
wget http://gisweb.ciat.cgiar.org/TRMM/SRTM_Resampled_250m/SRTM_SE_250m_TIF.rar && \
wget http://gisweb.ciat.cgiar.org/TRMM/SRTM_Resampled_250m/SRTM_W_250m_TIF.rar && \
unrar e -y SRTM_NE_250m_TIF.rar && \
unrar e -y SRTM_SE_250m_TIF.rar && \
unrar e -y SRTM_W_250m_TIF.rar