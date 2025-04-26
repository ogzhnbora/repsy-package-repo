package com.example.storage.object;

import org.springframework.web.multipart.MultipartFile;

public interface StorageStrategy {
    void store(String packageName, String version, MultipartFile file, String fileName) throws Exception;
}
