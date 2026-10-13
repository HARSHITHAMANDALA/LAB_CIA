FROM maven:3.9.9-eclipse-temurin-17

WORKDIR /app

COPY pom.xml .
COPY course-registration-app/pom.xml course-registration-app/pom.xml
COPY course-registration-app/src course-registration-app/src

CMD ["mvn", "-B", "clean", "verify"]
