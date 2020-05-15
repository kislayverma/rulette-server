package com.github.kislayverma.rulette.rest.rule;

import com.github.kislayverma.rulette.RuleSystem;
import com.github.kislayverma.rulette.core.exception.RuleConflictException;
import com.github.kislayverma.rulette.core.rule.Rule;
import com.github.kislayverma.rulette.rest.exception.RuleNotFoundException;
import com.github.kislayverma.rulette.rest.model.PaginatedResult;
import com.github.kislayverma.rulette.rest.rulesystem.RuleSystemFactory;
import com.github.kislayverma.rulette.rest.utils.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class RuleService {
    @Autowired
    private RuleSystemFactory ruleSystemFactory;

    public List<Rule> getAllRules(String providerName, String ruleSystemName) {
        return getRuleSystem(providerName, ruleSystemName).getAllRules();
    }

    public PaginatedResult<Rule> getRules(String providerName, String ruleSystemName, int pageNum, int pageSize) {
        return PaginationUtil.getPaginatedData(getAllRules(providerName, ruleSystemName), pageNum, pageSize);
    }

    public Rule getRuleById(String providerName, String ruleSystemName, String ruleId) {
        Rule rule = getRuleSystem(providerName, ruleSystemName).getRule(ruleId);
        if (rule == null) {
            throw new RuleNotFoundException("No rule defined for id " + ruleId);
        }

        return rule;
    }

    public Rule getApplicableRule(String providerName, String ruleSystemName, Map<String, String> inputMap) {
        return getRuleSystem(providerName, ruleSystemName).getRule(inputMap);
    }

    public Rule getNextApplicableRule(String providerName, String ruleSystemName, Map<String, String> inputMap) {
        return getRuleSystem(providerName, ruleSystemName).getNextApplicableRule(inputMap);
    }

    public Rule addRule(String providerName, String ruleSystemName, Map<String, String> inputMap) throws RuleConflictException {
        return getRuleSystem(providerName, ruleSystemName).addRule(inputMap);
    }

    public Rule updateRule(String providerName, String ruleSystemName, String ruleId, Map<String, String> inputMap) throws RuleConflictException {
        final RuleSystem rs = getRuleSystem(providerName, ruleSystemName);
        final Rule oldRule = getRuleById(providerName, ruleSystemName, ruleId);
        Rule modifiedRule = getRuleById(providerName, ruleSystemName, ruleId);
        for (Map.Entry<String, String> e : inputMap.entrySet()) {
            modifiedRule = modifiedRule.setColumnData(e.getKey(), e.getValue());
        }

        return rs.updateRule(oldRule, modifiedRule);
    }

    public void deleteRule(String providerName, String ruleSystemName, String ruleId) {
        getRuleSystem(providerName, ruleSystemName).deleteRule(ruleId);
    }

    public void deleteRule(String providerName, String ruleSystemName, Map<String, String> inputMap) {
        final RuleSystem rs = getRuleSystem(providerName, ruleSystemName);
        rs.deleteRule(new Rule(rs.getMetaData(), inputMap));
    }

    private RuleSystem getRuleSystem(final String providerName, final String ruleSystemName) {
        return ruleSystemFactory.getRuleSystem(providerName, ruleSystemName);
    }
}
