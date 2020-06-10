FROM java:8

EXPOSE 8001

ADD build/libs/section-0.0.1-SNAPSHOT.jar section-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","section-0.0.1-SNAPSHOT.jar"]