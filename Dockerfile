FROM openjdk:11
ADD target/resource-monitor*.jar /app/resource-monitor.jar

COPY scripts/* /scripts/
RUN chmod +x /scripts/*.sh

ENTRYPOINT java -jar /app/resource-monitor.jar