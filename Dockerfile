FROM openjdk:8-jre
WORKDIR /
ADD target/ruoyi.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar"]
CMD ["app.jar"]