package com.github.kislayverma.rulette.rest;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "rulesystems")
public class RuleSystemConfigList {
    private List<RuleSystemConfig> ruleSystemConfigList;

    public List<RuleSystemConfig> getRuleSystemConfigList() {
        return ruleSystemConfigList;
    }

    public void setRuleSystemConfigList(List<RuleSystemConfig> ruleSystemConfigList) {
        this.ruleSystemConfigList = ruleSystemConfigList;
    }

    @Override
    public String toString() {
        return "RuleSystemConfigList{" +
            "ruleSystemConfigList=" + ruleSystemConfigList +
            '}';
    }
}
