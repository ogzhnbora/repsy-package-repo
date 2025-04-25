# Kullanılacak base image
FROM openjdk:17-jdk-slim

# Çalışma dizini belirleniyor
WORKDIR /app

# Proje dosyalarını konteyner içine kopyala
COPY target/demo-0.0.1-SNAPSHOT.jar demo.jar

# Uygulama çalıştırılacak komut
CMD ["java", "-jar", "demo.jar"]

# Expose port
EXPOSE 8080
