package com.github.kislayverma.rulette.rest.rule;

import java.util.Map;

public class RuleDto {
    private Map<String, String> ruleInputs;

    public Map<String, String> getRuleInputs() {
        return ruleInputs;
    }

    public void setRuleInputs(Map<String, String> ruleInputs) {
        this.ruleInputs = ruleInputs;
    }
}
