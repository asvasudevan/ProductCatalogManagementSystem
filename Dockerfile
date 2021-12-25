FROM openjdk:11
ENV JAVA_HOME /Users/vasu/.sdkman/candidates/java/17.0.1-oracle
ENV PATH $PATH:/Users/vasu/.sdkman/candidates/java/17.0.1-oracle/bin
ADD target/pcms.jar pcms.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","/pcms.jar"]