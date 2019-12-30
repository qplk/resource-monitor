FROM openjdk:11
ADD target/resource-monitor*.jar /app/resource-monitor.jar

COPY scripts/* /bin/

ENTRYPOINT ["/bin/run.sh"]

EXPOSE 8080