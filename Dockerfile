FROM azul/zulu-openjdk-alpine:17.0.5-17.38.21-jre-headless-arm64 as build

MAINTAINER jzheng jiangzheng1023@foxmail.com

RUN mkdir /app

WORKDIR /app

ENV _JAVA_OPTIONS="-Xmx512m -Xms256m"
ENV SPRING_PROFILES_ACTIVE=prod,api-docs
ENV MANAGEMENT_METRICS_EXPORT_PROMETHEUS_ENABLED=true
ENV SPRING_DATASOURCE_URL="jdbc:mysql://mysql_default:3306/blog?useUnicode=true&characterEncoding=utf8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC&createDatabaseIfNotExist=true&allowPublicKeyRetrieval=true"
ENV SPRING_LIQUIBASE_URL="jdbc:mysql://mysql_default:3306/blog?useUnicode=true&characterEncoding=utf8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC&createDatabaseIfNotExist=true&allowPublicKeyRetrieval=true"
ENV JHIPSTER_SLEEP=30

COPY ./target/blog-0.0.1-SNAPSHOT.jar /app/blog.jar

EXPOSE 8080

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /app/blog.jar" ]
