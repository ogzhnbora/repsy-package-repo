package com.example.demo.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class StorageServiceFactory {

    @Autowired
    private ApplicationContext context;

    public StorageStrategy getStorageStrategy(String strategyName) {
        return (StorageStrategy) context.getBean(strategyName);
    }
}
