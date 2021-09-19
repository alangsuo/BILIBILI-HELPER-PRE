FROM openjdk:8u302-jre

MAINTAINER JunzhouLiu Junzhou2016@outlook.com

ADD target/BILIBILI-HELPER-*.RELEASE.jar /home/BILIBILI-HELPER.jar

ENTRYPOINT ["java","-jar","/home/BILIBILI-HELPER.jar"]