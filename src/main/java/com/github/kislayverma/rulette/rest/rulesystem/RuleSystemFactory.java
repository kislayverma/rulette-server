package com.github.kislayverma.rulette.rest.rulesystem;

import com.github.kislayverma.rulette.RuleSystem;
import com.github.kislayverma.rulette.rest.config.RuleSystemConfig;
import com.github.kislayverma.rulette.rest.config.RuleSystemConfigList;
import com.github.kislayverma.rulette.rest.exception.BadServerException;
import com.github.kislayverma.rulette.rest.exception.RuleSystemNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
    public synchronized void init() {
        LOGGER.info("Loading rule systems...");
        ruleSystemConfigList.getConfigs()
            .forEach(config -> {
                loadRuleSystem(config.getName(), config);
            });
    }

    public List<RuleSystem> getAllRuleSystems() {
        return new ArrayList<>(RULE_SYSTEM_MAP.values());
    }

    public RuleSystem getRuleSystem(String ruleSystemName) {
        return getRuleSystemInternal(ruleSystemName)
            .orElseThrow(() -> {
                return new RuleSystemNotFoundException("Rule system with name " + ruleSystemName + " not found");
            });
    }

    public synchronized void reloadRuleSystem(String ruleSystemName) {
        RuleSystemConfig config = getConfig(ruleSystemName).orElseThrow(() -> {
            return new RuleSystemNotFoundException("Rule system with name " + ruleSystemName + " not found");
        });
        loadRuleSystem(ruleSystemName, config);
    }

    private synchronized void loadRuleSystem(String ruleSystemName, RuleSystemConfig config) {
        try {
            RULE_SYSTEM_MAP.put(ruleSystemName, buildRuleSystem(ruleSystemName, config));
        } catch (Exception e) {
            throw new BadServerException("Rule System with name " + ruleSystemName + " could not be loaded", e);
        }
    }

    private RuleSystem buildRuleSystem(String ruleSystemName, RuleSystemConfig config) {
        try {
            return new RuleSystem(ruleSystemName, providerFactory.getProvider(config));
        } catch (Exception e) {
            throw new BadServerException("Rule System with name " + ruleSystemName + " could not be loaded", e);
        }
    }

    private Optional<RuleSystem> getRuleSystemInternal(String ruleSystemName) {
        if (ruleSystemName == null || ruleSystemName.trim().isEmpty()) {
            throw new RuntimeException("Rule system name not provided");
        }
        return Optional.ofNullable(RULE_SYSTEM_MAP.get(ruleSystemName));
    }

    private Optional<RuleSystemConfig> getConfig(String ruleSystemName) {
        for (RuleSystemConfig config : ruleSystemConfigList.getConfigs()) {
            if (ruleSystemName.equals(config.getName())) {
                return Optional.of(config);
            }
        }
        return Optional.empty();
    }
}
