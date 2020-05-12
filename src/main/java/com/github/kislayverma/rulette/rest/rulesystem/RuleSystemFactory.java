package com.github.kislayverma.rulette.rest.rulesystem;

import com.github.kislayverma.rulette.RuleSystem;
import com.github.kislayverma.rulette.rest.config.RuleSystemConfigList;
import com.github.kislayverma.rulette.rest.exception.BadServerException;
import com.github.kislayverma.rulette.rest.exception.RuleSystemNotFoundException;
import com.github.kislayverma.rulette.rest.provider.DataProviderService;
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
    private DataProviderService providerService;

    private final Map<String, RuleSystem> RULE_SYSTEM_MAP = new ConcurrentHashMap<>();

    @PostConstruct
    public synchronized void init() {
        LOGGER.info("Loading all rule systems from all providers...");
        providerService.getAllProviderConfigs()
            .stream()
            .map(providerConfig -> providerService.getProvider(providerConfig.getName()))
            .flatMap(provider -> provider.getAllRuleSystemMetaData().stream())
            .forEach(ruleSystemMetaData -> loadRuleSystem(ruleSystemMetaData.getRuleSystemName()));
    }

    public List<RuleSystem> getAllRuleSystems() {
        LOGGER.info("Returning {} rule systems", RULE_SYSTEM_MAP.size());
        return new ArrayList<>(RULE_SYSTEM_MAP.values());
    }

    public RuleSystem getRuleSystem(String ruleSystemName) {
        return getRuleSystemInternal(ruleSystemName)
            .orElseThrow(() -> new RuleSystemNotFoundException("Rule system with name " + ruleSystemName + " not found"));
    }

    public synchronized void reloadRuleSystem(String ruleSystemName) {
        LOGGER.info("Reloading rule system {}", ruleSystemName);
        loadRuleSystem(ruleSystemName);
    }

    private synchronized void loadRuleSystem(String ruleSystemName) {
        try {
            LOGGER.info("Loading rule system {}", ruleSystemName);
            RULE_SYSTEM_MAP.put(ruleSystemName, buildRuleSystem(ruleSystemName));
        } catch (Exception e) {
            throw new BadServerException("Rule System with name " + ruleSystemName + " could not be loaded", e);
        }
    }

    public void deleteRuleSystem(String ruleSystemName) {
        RULE_SYSTEM_MAP.remove(ruleSystemName);
    }

    private RuleSystem buildRuleSystem(String ruleSystemName) {
        try {
            LOGGER.info("Constructing rule system {}", ruleSystemName);
            return new RuleSystem(ruleSystemName, providerService.getProviderForRuleSystem(ruleSystemName));
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
}
