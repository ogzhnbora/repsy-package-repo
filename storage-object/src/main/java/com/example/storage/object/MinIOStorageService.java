package com.example.storage.object;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Service("minioStorage")
public class MinIOStorageService implements StorageStrategy {

    private final MinioClient minioClient;
    private final String bucketName = "repsy-packages";

    public MinIOStorageService() {
        this.minioClient = MinioClient.builder()
                .endpoint("http://localhost:9000")
                .credentials("minioadmin", "minioadmin")
                .build();
    }

    @Override
    public void store(String packageName, String version, MultipartFile file, String fileName) throws Exception {
        String objectName = packageName + "/" + version + "/" + fileName;
        try (InputStream is = file.getInputStream()) {
            minioClient.putObject(
                PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .stream(is, file.getSize(), -1)
                    .contentType("application/octet-stream")
                    .build()
            );
        }
    }
}
