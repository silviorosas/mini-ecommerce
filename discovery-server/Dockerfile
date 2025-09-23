# Usa una imagen base de Java
FROM eclipse-temurin:17-jdk-focal

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el archivo JAR de tu aplicación al contenedor.
# Asegúrate de que el nombre del archivo JAR sea el correcto si no es el predeterminado.
COPY target/*.jar app.jar

# Comando para ejecutar la aplicación cuando el contenedor se inicie
ENTRYPOINT ["java","-jar","/app/app.jar"]