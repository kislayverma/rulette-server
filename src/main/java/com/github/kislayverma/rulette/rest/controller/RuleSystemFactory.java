package com.github.kislayverma.rulette.rest.controller;

import com.github.kislayverma.rulette.RuleSystem;
import com.github.kislayverma.rulette.core.data.IDataProvider;
import com.github.kislayverma.rulette.rest.Configuration;
import com.github.kislayverma.rulette.rest.exception.RuleSystemException;
import com.github.kislayverma.rulette.rest.exception.StorageEngineException;
import com.github.kislayverma.rulette.rest.storage.DataProviderFactory;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RuleSystemFactory {

    private static final Map<String, RuleSystem> RULE_SYSTEM_MAP = new ConcurrentHashMap<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(RuleSystemFactory.class);

    private final IDataProvider dataProvider;

    public RuleSystemFactory(String storageEngineType, String storageConfigFilePath) throws StorageEngineException {
        this.dataProvider = DataProviderFactory.getDataProvider(storageEngineType, storageConfigFilePath);
    }

    public RuleSystem getRuleSystem(String ruleSystemName) throws RuleSystemException {
        if (ruleSystemName == null) {
            throw new RuntimeException("Rule system name not provided");
        }

        RuleSystem rs = RULE_SYSTEM_MAP.get(ruleSystemName);
        if (rs != null) {
            return rs;
        } else {
            synchronized (RULE_SYSTEM_MAP) {
                try {
                    rs = loadRuleSystem(ruleSystemName);
                    if (rs != null) {
                        RULE_SYSTEM_MAP.put(ruleSystemName, rs);
                        return rs;
                    }
                } catch (Exception ex) {
                    LOGGER.error("Error cresting rule system with name " + ruleSystemName, ex);
                    throw new RuleSystemException("Error cresting rule system with name " + ruleSystemName, ex);
                }
            }
        }

        return null;
    }

    public void reloadRuleSystem(String ruleSystemName) {
        synchronized (RULE_SYSTEM_MAP) {
            RULE_SYSTEM_MAP.remove(ruleSystemName);
        }
    }

    private RuleSystem loadRuleSystem(String ruleSystemName) throws Exception {
        LOGGER.error("Loading ruLe system with name " + ruleSystemName);
        return new RuleSystem(ruleSystemName, dataProvider);
    }
}
