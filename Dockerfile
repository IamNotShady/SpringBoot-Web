FROM java:8u66-jdk
ADD target/SpringBoot-web.jar /opt/SpringBoot-web.jar
EXPOSE 8080
CMD java -Djava.security.egd=file:/dev/./urandom -jar /opt/SpringBoot-web.jar
