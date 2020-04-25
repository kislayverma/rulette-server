package com.github.kislayverma.rulette.rest.rulesystem;

import com.github.kislayverma.rulette.core.data.IDataProvider;
import com.github.kislayverma.rulette.mysql.MysqlDataProvider;
import com.github.kislayverma.rulette.rest.config.RuleSystemConfig;
import com.github.kislayverma.rulette.rest.exception.BadClientException;
import com.github.kislayverma.rulette.rest.exception.BadServerException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

@Component
public class RuleSystemProviderFactory {
    public IDataProvider getProvider(RuleSystemConfig rsConfig) {
        if ("mysql".equals(rsConfig.getProviderType())) {
            return buildMySqlProvider(rsConfig);
        } else {
            throw new BadClientException("Provider type " + rsConfig.getProviderType() + " is not supported");
        }
    }

    private IDataProvider buildMySqlProvider(RuleSystemConfig rsConfig) {
        try {
            return new MysqlDataProvider(convertConfigToProperties(rsConfig));
        } catch (IOException | SQLException e) {
            throw new BadServerException("Could not create rule system with name " + rsConfig.getName(), e);
        }
    }

    private Properties convertConfigToProperties(RuleSystemConfig rsConfig) {
        Properties props = new Properties();
        props.setProperty("driverClass", rsConfig.getDriverClass());
        props.setProperty("jdbcUrl", rsConfig.getJdbcUrl());
        props.setProperty("username", rsConfig.getUsername());
        props.setProperty("password", rsConfig.getPassword());
        props.setProperty("maxPoolSize", rsConfig.getMaxPoolSize().toString());
        props.setProperty("connectionTimeout", rsConfig.getConnectionTimeout().toString());

        return props;
    }
}
