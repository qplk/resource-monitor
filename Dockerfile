FROM openjdk:11
ADD target/resource-monitor*.jar /app/resource-monitor.jar

COPY scripts/* /scripts/
ADD src/main/java/resources/application.yaml /app/resources/application.yaml
RUN chmod +x /scripts/*.sh

ENTRYPOINT java -Dspring.config.location=/app/resources/application.yaml -jar /app/resource-monitor.jar