package com.github.kislayverma.rulette.rest.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "rulesystems")
public class RuleSystemConfigList {
    private List<RuleSystemConfig> configs;

    public List<RuleSystemConfig> getConfigs() {
        return configs;
    }

    public void setConfigs(List<RuleSystemConfig> configs) {
        this.configs = configs;
    }

    @Override
    public String toString() {
        return "RuleSystemConfigList{" +
            "configs=" + configs +
            '}';
    }
}
