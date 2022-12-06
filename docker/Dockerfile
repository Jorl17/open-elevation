FROM osgeo/gdal:ubuntu-small-latest-amd64

RUN apt-get update
ARG DEBIAN_FRONTEND=noninteractive
RUN apt-get install -y libspatialindex-dev unar bc python3-pip wget

ADD ./requirements.txt .
RUN pip install -r requirements.txt

RUN mkdir /code
ADD . /code/

WORKDIR /code
CMD python3 server.py

EXPOSE 8080

EXPOSE 8443
