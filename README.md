# Repsy Package Management Backend

This project is a Spring Boot-based package management backend developed as part of the Junior FullStack Developer Assignment.

It allows users to:
- Upload packages (meta.json + package.rep)
- Download package files
- Store packages using different storage strategies (Filesystem or MinIO Object Storage)

---

## 📦 Features

- Upload package metadata and package files
- Download specific package files
- Storage Strategy Pattern:
  - FileSystemStorage (local disk)
  - MinIOStorage (object storage service)
- Dockerized Spring Boot Application
- Integrated with PostgreSQL database
- Repsy Private Maven Repository for library distribution

---

## 🚀 Getting Started

### Requirements
- Docker & Docker Compose installed
- Java 17+
- Maven

### Run with Docker Compose (Recommended)

```bash
docker pull oguzhanbora/repsy-app:latest

docker run -d -p 8080:8080 \
-e SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/repsydb
  -e SPRING_DATASOURCE_USERNAME=postgres\
  -e SPRING_DATASOURCE_PASSWORD=12345\
  -e STORAGE_STRATEGY=fileSystemStorage \
  oguzhanbora/repsy-app:latest
