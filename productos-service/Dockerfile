# Usa una imagen base de Java
FROM eclipse-temurin:17

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el archivo JAR de tu aplicación al contenedor.
COPY target/*.jar app.jar

# Copia el script wait-for-it.sh y le da permisos de ejecución
COPY wait-for-it.sh /usr/local/bin/wait-for-it.sh
RUN chmod +x /usr/local/bin/wait-for-it.sh

# Comando para ejecutar la aplicación cuando el contenedor se inicie
ENTRYPOINT ["java","-jar","/app/app.jar"]
