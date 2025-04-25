package com.example.demo.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "packages")
public class PackageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;
    private String version;
    private String author;

    @OneToMany(mappedBy = "packageEntity", cascade = CascadeType.ALL)
    private List<DependencyEntity> dependencies;

    // Getter – Setter

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<DependencyEntity> getDependencies() {
        return dependencies;
    }

    public void setDependencies(List<DependencyEntity> dependencies) {
        this.dependencies = dependencies;
    }
}
