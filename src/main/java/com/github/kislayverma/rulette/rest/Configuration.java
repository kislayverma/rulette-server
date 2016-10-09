package com.github.kislayverma.rulette.rest;

import com.github.kislayverma.rulette.rest.controller.RuleSystemController;
import com.github.kislayverma.rulette.rest.controller.RuleSystemFactory;
import java.util.Properties;

import org.restexpress.RestExpress;
import org.restexpress.util.Environment;

public class Configuration extends Environment {

    private static final String DEFAULT_EXECUTOR_THREAD_POOL_SIZE = "20";

    private static final String PORT_PROPERTY = "port";
    private static final String BASE_URL_PROPERTY = "base.url";
    private static final String EXECUTOR_THREAD_POOL_SIZE = "executor.threadPool.size";
    private static final String STORAGE_ENGINE = "storage.engine";
    private static final String STORAGE_CONFIG_FILE_PATH = "storage.configFilePath";

    private int port;
    private String baseUrl;
    private int executorThreadPoolSize;
    private String storageEngine;
    private String storageConfigFilePath;

    private RuleSystemController ruleSystemController;

    @Override
    protected void fillValues(Properties p) {
        this.port = Integer.parseInt(p.getProperty(PORT_PROPERTY, String.valueOf(RestExpress.DEFAULT_PORT)));
        this.baseUrl = p.getProperty(BASE_URL_PROPERTY, "http://localhost:" + String.valueOf(port));
        this.executorThreadPoolSize = Integer.parseInt(p.getProperty(EXECUTOR_THREAD_POOL_SIZE, DEFAULT_EXECUTOR_THREAD_POOL_SIZE));

        this.storageEngine = p.getProperty(STORAGE_ENGINE);
        this.storageConfigFilePath = p.getProperty(STORAGE_CONFIG_FILE_PATH);
    }

    public void initComponents() {
        try {
            RuleSystemFactory ruleSystemFactory =
                new RuleSystemFactory(this.storageEngine, this.storageConfigFilePath);
            ruleSystemController = new RuleSystemController(ruleSystemFactory);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public int getPort() {
        return port;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public int getExecutorThreadPoolSize() {
        return executorThreadPoolSize;
    }

    public RuleSystemController getRuleSystemController() {
        return ruleSystemController;
    }

    public String getStorageEngine() {
        return storageEngine;
    }

    public String getStorageConfigFilePath() {
        return storageConfigFilePath;
    }
}
