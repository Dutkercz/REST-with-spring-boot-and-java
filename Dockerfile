# Usa como imagem base o Eclipse Temurin (implementação do OpenJDK)
# na versão 21 com suporte a desenvolvimento e execução de aplicações Java (JDK).
FROM eclipse-temurin:21-jdk

# Copia o arquivo JAR gerado pelo build (na pasta target)
# para dentro do container, renomeando para "app.jar".
COPY target/*.jar app.jar

# Define o comando padrão que será executado quando o container iniciar:
# Executa "java -jar app.jar".
ENTRYPOINT ["java","-jar","app.jar"]

#depois devemos executar, via terminal, o comando "docker build -t rest-with-spring-boot-and-java:0.0.1-SNAPSHOT"
#OBS:
#a tag REST-with-spring-boot-and-java-0.0.1-SNAPSHOT deve estar em letras minusculas