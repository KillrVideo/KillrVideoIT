FROM maven:3.6-jdk-8

WORKDIR /root

COPY pom.xml /root/
COPY src/ /root/src

RUN mvn dependency:copy-dependencies dependency:resolve-plugins test -DskipTests

CMD ["mvn", "surefire:test"]
