package com.github.kislayverma.rulette.rest;

import com.github.kislayverma.rulette.RuleSystem;
import com.github.kislayverma.rulette.rest.exception.BadServerException;
import com.github.kislayverma.rulette.rest.exception.RuleSystemNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RuleSystemFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(RuleSystemFactory.class);

    @Autowired
    private RuleSystemConfigList ruleSystemConfigList;

    @Autowired
    private RuleSystemProviderFactory providerFactory;

    private final Map<String, RuleSystem> RULE_SYSTEM_MAP = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        LOGGER.info("Loading rule systems...");
        ruleSystemConfigList.getRuleSystemConfigList()
            .forEach(config -> {
                try {
                    RULE_SYSTEM_MAP.put(
                        config.getName(), new RuleSystem(config.getName(), providerFactory.getProvider(config)));
                } catch (Exception e) {
                    throw new BadServerException("Could not instantiate rule system with name "+ config.getName(), e);
                }
            });
    }

    public RuleSystem getRuleSystem(String ruleSystemName) {
        if (ruleSystemName == null || ruleSystemName.trim().isEmpty()) {
            throw new RuntimeException("Rule system name not provided");
        }

        RuleSystem rs = RULE_SYSTEM_MAP.get(ruleSystemName);
        if (rs == null) {
            throw new RuleSystemNotFoundException("Rule system with name " + ruleSystemName + " not found");
        }

        return rs;
    }

    public void reloadRuleSystem(String ruleSystemName) {
        synchronized (RULE_SYSTEM_MAP) {
            RULE_SYSTEM_MAP.put(ruleSystemName, loadRuleSystem(ruleSystemName));
        }
    }

    private RuleSystem loadRuleSystem(String ruleSystemName) {
        try {
            return new RuleSystem(ruleSystemName, null);
        } catch (Exception e) {
            throw new BadServerException("Rule System with name " + ruleSystemName + " could not be loaded", e);
        }
    }
}
