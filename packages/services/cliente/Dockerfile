FROM maven:3-openjdk-8 as build
WORKDIR /workspace/app

COPY pom.xml .
COPY src src

RUN mvn install
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

FROM openjdk:8-jdk-alpine

VOLUME /tmp
ARG DEPENDENCY=/workspace/app/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

EXPOSE 9090

ENTRYPOINT ["java","-cp","app:app/lib/*","com.pricinghub.cliente.App"]
