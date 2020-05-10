package com.github.kislayverma.rulette.rest.provider;

import com.github.kislayverma.rulette.core.data.IDataProvider;
import com.github.kislayverma.rulette.rest.config.RuleSystemConfigList;
import com.github.kislayverma.rulette.rest.exception.BadClientException;
import com.github.kislayverma.rulette.rest.exception.ProviderNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This service loads the rule system configuration from the input config file and provides access to data provider
 * objects
 */
@Component
public class DataProviderService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataProviderService.class);

    @Autowired
    private RuleSystemConfigList ruleSystemConfigList;

    @Autowired
    private DataProviderFactory providerFactory;

    private final Map<String, IDataProvider> ruleSystemProviderMap = new HashMap<>();

    private final Map<String, String> ruleSystemProviderNameMap = new HashMap<>();

    @PostConstruct
    public final void init() {
        ruleSystemConfigList.getSystems().stream().forEach(config -> {
            ruleSystemProviderMap.put(config.getName(), providerFactory.getProvider(config.getProvider()));
            ruleSystemProviderNameMap.put(config.getName(), config.getProvider());
        });
    }

    public List<DataProviderConfig> getAllProviderConfigs() {
        return providerFactory.getAllProviderConfigs();
    }

    public DataProviderConfig getProviderConfig(String providerName) {
        if (providerName == null || providerName.trim().isEmpty()) {
            throw new BadClientException("No provider name given");
        }

        return providerFactory.getProviderConfig(providerName);
    }

    public DataProviderConfig getProviderConfigForRuleSystem(String ruleSystemName) {
        if (ruleSystemName == null || ruleSystemName.trim().isEmpty()) {
            throw new BadClientException("No provider name given");
        }

        String providerName = ruleSystemProviderNameMap.get(ruleSystemName);
        if (providerName == null) {
            throw new ProviderNotFoundException("No provider found for rule system " + ruleSystemName);
        }

        return providerFactory.getProviderConfig(providerName);
    }

    public IDataProvider getProviderForRuleSystem(String ruleSystemName) {
        if (ruleSystemName == null || ruleSystemName.trim().isEmpty()) {
            throw new BadClientException("No rule system name given to load provider");
        }

        return ruleSystemProviderMap.get(ruleSystemName);
    }
}
