package com.example.demo.controller;

import com.example.demo.model.MetaData;
import com.example.demo.model.Dependency;
import com.example.demo.model.PackageEntity;
import com.example.demo.model.DependencyEntity;
import com.example.demo.repository.PackageRepository;
import com.example.demo.storage.StorageServiceFactory;
import com.example.demo.storage.StorageStrategy;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
public class DeployController {

    private final PackageRepository packageRepository;
    private final StorageServiceFactory storageServiceFactory;

    @Value("${storage.strategy}")
    private String strategyName;

    @Autowired
    public DeployController(PackageRepository packageRepository, StorageServiceFactory storageServiceFactory) {
        this.packageRepository = packageRepository;
        this.storageServiceFactory = storageServiceFactory;
    }

    @PostMapping("/{packageName}/{version}")
    public ResponseEntity<String> uploadPackage(
            @PathVariable String packageName,
            @PathVariable String version,
            @RequestPart("meta") MultipartFile metaJson,
            @RequestPart("file") MultipartFile repFile
    ) {
        try {
            // 1. meta.json -> MetaData objesi
            String json = new String(metaJson.getBytes());
            ObjectMapper mapper = new ObjectMapper();
            MetaData meta = mapper.readValue(json, MetaData.class);

            // 2. Veritabanı modeli oluştur
            PackageEntity pkg = new PackageEntity();
            pkg.setName(meta.getName());
            pkg.setVersion(meta.getVersion());
            pkg.setAuthor(meta.getAuthor());

            List<DependencyEntity> dependencyEntities = new ArrayList<>();
            for (Dependency dep : meta.getDependencies()) {
                DependencyEntity depEntity = new DependencyEntity();
                depEntity.setPackageName(dep.getPackageName());
                depEntity.setVersion(dep.getVersion());
                depEntity.setPackageEntity(pkg);
                dependencyEntities.add(depEntity);
            }
            pkg.setDependencies(dependencyEntities);

            // 3. Kaydet
            packageRepository.save(pkg);

            // 4. Dosyayı stratejiye göre kaydet
            StorageStrategy strategy = storageServiceFactory.getStorageStrategy(strategyName);
            strategy.store(packageName, version, repFile, "package.rep");

            return ResponseEntity.ok("Paket başarıyla kaydedildi ve dosya yüklendi!");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Hata oluştu: " + e.getMessage());
        }
    }

    @GetMapping("/{packageName}/{version}/{fileName}")
    public ResponseEntity<Resource> downloadPackageFile(
            @PathVariable String packageName,
            @PathVariable String version,
            @PathVariable String fileName
    ) {
        try {
            String basePath = "uploads/packages/" + packageName + "/" + version;
            File file = new File(basePath + "/" + fileName);

            if (!file.exists()) {
                return ResponseEntity.notFound().build();
            }

            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                    .contentLength(file.length())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
