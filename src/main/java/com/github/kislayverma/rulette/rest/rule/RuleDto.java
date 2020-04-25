package com.github.kislayverma.rulette.rest.rule;

import java.util.HashMap;
import java.util.Map;

public class RuleDto {
    private Map<String, String> ruleInputs = new HashMap<>();

    public Map<String, String> getRuleInputs() {
        return ruleInputs;
    }

    public void setRuleInputs(Map<String, String> ruleInputs) {
        this.ruleInputs = ruleInputs;
    }
}
