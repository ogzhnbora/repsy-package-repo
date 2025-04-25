package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Dependency {

    @JsonProperty("package")  // Burası önemli
    private String packageName;

    private String version;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
