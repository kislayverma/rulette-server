package com.github.kislayverma.rulette.rest.api.rulesystem;

import com.github.kislayverma.rulette.RuleSystem;
import com.github.kislayverma.rulette.core.data.IDataProvider;
import com.github.kislayverma.rulette.core.metadata.RuleSystemMetaData;
import com.github.kislayverma.rulette.rest.exception.BadServerException;
import com.github.kislayverma.rulette.rest.exception.ProviderNotFoundException;
import com.github.kislayverma.rulette.rest.exception.RuleSystemNotFoundException;
import com.github.kislayverma.rulette.rest.api.provider.DataProviderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class RuleSystemFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(RuleSystemFactory.class);

    @Autowired
    private DataProviderService providerService;

    private final Map<String, Map<String, RuleSystem>> PROVIDER_RULE_SYSTEM_MAP = new ConcurrentHashMap<>();

    @PostConstruct
    public synchronized void init() {
        LOGGER.info("Loading all rule systems from all providers...");
        providerService.getAllProviderConfigs()
            .stream()
            .forEach(providerConfig -> {
                IDataProvider provider = providerService.getProvider(providerConfig.getName());
                Map<String, RuleSystemMetaData> configNameProviderMap = new HashMap<>();
                provider.getAllRuleSystemMetaData()
                    .stream()
                    .forEach(rs -> loadRuleSystem(providerConfig.getName(), rs.getRuleSystemName()));
            });
    }

    public List<RuleSystem> getAllRuleSystems() {
        LOGGER.info("Returning {} rule systems", PROVIDER_RULE_SYSTEM_MAP.size());

        return PROVIDER_RULE_SYSTEM_MAP.values()
            .stream()
            .flatMap(rsMap-> rsMap.values().stream())
            .collect(Collectors.toList());
    }

    public RuleSystem getRuleSystem(String providerName, String ruleSystemName) {
        return getRuleSystemInternal(providerName, ruleSystemName)
            .orElseThrow(() -> new RuleSystemNotFoundException("Rule system with name " + ruleSystemName + " not found"));
    }

    public synchronized void reloadRuleSystem(String providerName, String ruleSystemName) {
        LOGGER.info("Reloading rule system {}", ruleSystemName);
        loadRuleSystem(providerName, ruleSystemName);
    }

    private synchronized void loadRuleSystem(String providerName, String ruleSystemName) {
        try {
            LOGGER.info("Loading rule system {}", ruleSystemName);
            PROVIDER_RULE_SYSTEM_MAP.putIfAbsent(providerName, new HashMap<>());
            Map<String, RuleSystem> rsMap = PROVIDER_RULE_SYSTEM_MAP.get(providerName);
            rsMap.put(ruleSystemName, buildRuleSystem(providerName, ruleSystemName));
            PROVIDER_RULE_SYSTEM_MAP.put(providerName, rsMap);
        } catch (Exception e) {
            throw new BadServerException("Rule System with name " + ruleSystemName + " could not be loaded", e);
        }
    }

    public void deleteRuleSystem(String providerName, String ruleSystemName) {
        Map<String, RuleSystem> rsMap = PROVIDER_RULE_SYSTEM_MAP.get(providerName);
        if (rsMap == null) {
            throw new ProviderNotFoundException("Provider with name " + providerName + " not found");
        }

        rsMap.remove(ruleSystemName);
    }

    private RuleSystem buildRuleSystem(String providerName, String ruleSystemName) {
        try {
            LOGGER.info("Constructing rule system {}", ruleSystemName);
            return new RuleSystem(ruleSystemName, providerService.getProvider(providerName));
        } catch (Exception e) {
            throw new BadServerException("Rule System with name " + ruleSystemName + " could not be loaded", e);
        }
    }

    private Optional<RuleSystem> getRuleSystemInternal(String providerName, String ruleSystemName) {
        if (providerName == null || providerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Provider name not provided");
        }
        if (ruleSystemName == null || ruleSystemName.trim().isEmpty()) {
            throw new IllegalArgumentException("Rule system name not provided");
        }
        Map<String, RuleSystem> rsMap = PROVIDER_RULE_SYSTEM_MAP.get(providerName);

        return rsMap == null ? Optional.empty() : Optional.ofNullable(rsMap.get(ruleSystemName));
    }
}
