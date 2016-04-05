FROM java:8
MAINTAINER Shiao-An Yuan <shiao.an.yuan@gmail.com>

COPY . /air-quailty-exporter
WORKDIR /air-quailty-exporter

RUN ./gradlew installDist

ENTRYPOINT ["build/install/air-quality-exporter/bin/air-quality-exporter"]
