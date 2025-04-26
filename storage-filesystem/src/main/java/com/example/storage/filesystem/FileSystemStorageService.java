package com.example.storage.filesystem;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;

@Service("fileSystemStorage")
public class FileSystemStorageService implements StorageStrategy {

    @Override
    public void store(String packageName, String version, MultipartFile file, String fileName) throws Exception {
        String basePath = "uploads/packages/" + packageName + "/" + version;
        File dir = new File(basePath);
        if (!dir.exists()) dir.mkdirs();

        File target = new File(dir, fileName);
        try (FileOutputStream fos = new FileOutputStream(target)) {
            fos.write(file.getBytes());
        }
    }
}
