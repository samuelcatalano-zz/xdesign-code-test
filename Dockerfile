FROM openjdk:11.0.5-jre-slim

MAINTAINER Samuel Catalano <samuel.catalano@gmail.com>

RUN mkdir -p /usr/share/xdesign && \
mkdir /var/run/xdesign && \
mkdir /var/log/xdesign

COPY /target/xdesigntest-1.0.0.jar /usr/share/xdesign/xdesigntest-1.0.0.jar
COPY /src/main/resources/munrotab_v6.2.csv /usr/share/xdesign/munrotab_v6.2.csv

WORKDIR /usr/share/xdesign/
EXPOSE 8080 8787

ENV TZ=Europe/London
ENV LC_ALL en_US.UTF-8
ENV LANG en_US.UTF-8
ENV LANGUAGE en_US.UTF-8
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

CMD ["java","-Djava.security.egd=file:/dev/./urandom", "-Dfile.encoding=UTF-8", "-jar","xdesigntest-1.0.0.jar"]