FROM geodata/gdal:2.1.3

ADD ./requirements.txt .

RUN pip install -r requirements.txt

RUN apt-get install -y libspatialindex-dev unrar-free bc

RUN mkdir /code

ADD . /code/

WORKDIR /code

CMD python server.py

EXPOSE 8080