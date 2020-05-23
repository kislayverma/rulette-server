package com.github.kislayverma.rulette.rest.api.provider;

import com.github.kislayverma.rulette.core.data.IDataProvider;
import com.github.kislayverma.rulette.mysql.MysqlDataProvider;
import com.github.kislayverma.rulette.rest.config.RuleSystemConfigList;
import com.github.kislayverma.rulette.rest.exception.BadClientException;
import com.github.kislayverma.rulette.rest.exception.BadServerException;
import com.github.kislayverma.rulette.rest.exception.ProviderNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

@Component
public class DataProviderFactory {

    private  static final Logger LOGGER = LoggerFactory.getLogger(DataProviderFactory.class);

    @Autowired
    private RuleSystemConfigList ruleSystemConfigList;

    private Map<String, DataProviderConfig> providerConfigMap = new HashMap<>();

    @PostConstruct
    public final void init() {
        LOGGER.info("Loading all data provider configurations from application config file");
        ruleSystemConfigList.getDataproviders()
            .stream()
            .map(dto -> {
                DataProviderConfig config = new DataProviderConfig();
                config.setName(dto.getName());
                config.setConnectionTimeout(dto.getConnectionTimeout());
                config.setDriverClass(dto.getDriverClass());
                config.setInitialPoolSize(dto.getInitialPoolSize());
                config.setJdbcUrl(dto.getJdbcUrl());
                config.setMaxPoolSize(dto.getMaxPoolSize());
                config.setMaxStatements(dto.getMaxStatements());
                config.setMinPoolSize(dto.getMinPoolSize());
                config.setPassword(dto.getPassword());
                config.setProviderType(DataProviderType.valueOf(dto.getProviderType().toUpperCase()));
                config.setUsername(dto.getUsername());

                return config;
            })
            .forEach(config -> {
                providerConfigMap.put(config.getName(), config);
            });
    }

    public List<DataProviderConfig> getAllProviderConfigs() {
        return new ArrayList<>(providerConfigMap.values());
    }

    /**
     * This method returns the data provider configuration mapped to the given string
     * @param providerName
     */
    public DataProviderConfig getProviderConfig(String providerName) {
        DataProviderConfig config = providerConfigMap.get(providerName);
        if (config == null) {
            throw new ProviderNotFoundException("No provider found with name " + providerName);
        }

        return config;
    }

    /**
     * This method constructs a new data provider instance using the configuration mapped to given string
     * @param providerName
     */
    public IDataProvider getProvider(String providerName) {
        DataProviderConfig config = providerConfigMap.get(providerName);
        if (config == null) {
            throw new ProviderNotFoundException("No provider found with name " + providerName);
        }
        if (config.getProviderType() == DataProviderType.MYSQL) {
            return buildMySqlProvider(config);
        } else {
            throw new BadClientException("Provider type " + config.getProviderType() + " is not supported");
        }
    }

    private MysqlDataProvider buildMySqlProvider(DataProviderConfig config) {
        try {
            return new MysqlDataProvider(convertConfigToProperties(config));
        } catch (IOException | SQLException e) {
            throw new BadServerException("Could not create MySQL backed provider with name " + config.getName(), e);
        }
    }

    private Properties convertConfigToProperties(DataProviderConfig config) {
        Properties props = new Properties();
        props.setProperty("driverClass", config.getDriverClass());
        props.setProperty("jdbcUrl", config.getJdbcUrl());
        props.setProperty("username", config.getUsername());
        props.setProperty("password", config.getPassword());
        props.setProperty("maxPoolSize", config.getMaxPoolSize().toString());
        props.setProperty("connectionTimeout", config.getConnectionTimeout().toString());

        return props;
    }
}
