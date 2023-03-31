FROM docker.io/library/eclipse-temurin:17-alpine
EXPOSE 8080
COPY maven /maven/
ENTRYPOINT ["java","-jar", "-Dkalix.proxy.internal-proxy-hostname=kalix-proxy","/maven/communication-manager-1.0-SNAPSHOT.jar"]
