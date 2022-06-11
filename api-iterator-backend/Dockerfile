FROM adoptopenjdk/openjdk9-openj9:jdk-9.0.4.12_openj9-0.9.0
RUN mkdir /opt/shareclasses
RUN mkdir /opt/app

COPY build/libs/*.jar /opt/app/spring-boot-application.jar

EXPOSE 8088
ENTRYPOINT ["java", "-Xmx128m", "-Xscmx50M", "-Xquickstart", "-Xshareclasses:cacheDir=/opt/shareclasses", "-jar", "/opt/app/spring-boot-application.jar"]
