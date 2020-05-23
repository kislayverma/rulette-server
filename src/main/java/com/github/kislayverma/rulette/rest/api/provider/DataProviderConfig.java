package com.github.kislayverma.rulette.rest.api.provider;

public class DataProviderConfig {
    private String name;
    private DataProviderType providerType;
    private String driverClass;
    private String jdbcUrl;
    private String username;
    private String password;
    private Integer initialPoolSize;
    private Integer maxPoolSize;
    private Integer minPoolSize;
    private Integer maxStatements;
    private Integer connectionTimeout;

    @Override
    public String toString() {
        return "DataProviderConfigDto{" +
            "name='" + name + '\'' +
            ", providerType='" + providerType + '\'' +
            ", driverClass='" + driverClass + '\'' +
            ", jdbcUrl='" + jdbcUrl + '\'' +
            ", username='" + username + '\'' +
            ", password='" + password + '\'' +
            ", initialPoolSize=" + initialPoolSize +
            ", maxPoolSize=" + maxPoolSize +
            ", minPoolSize=" + minPoolSize +
            ", maxStatements=" + maxStatements +
            ", connectionTimeout=" + connectionTimeout +
            '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DataProviderType getProviderType() {
        return providerType;
    }

    public void setProviderType(DataProviderType providerType) {
        this.providerType = providerType;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getInitialPoolSize() {
        return initialPoolSize;
    }

    public void setInitialPoolSize(Integer initialPoolSize) {
        this.initialPoolSize = initialPoolSize;
    }

    public Integer getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(Integer maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public Integer getMinPoolSize() {
        return minPoolSize;
    }

    public void setMinPoolSize(Integer minPoolSize) {
        this.minPoolSize = minPoolSize;
    }

    public Integer getMaxStatements() {
        return maxStatements;
    }

    public void setMaxStatements(Integer maxStatements) {
        this.maxStatements = maxStatements;
    }

    public Integer getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(Integer connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }
}
