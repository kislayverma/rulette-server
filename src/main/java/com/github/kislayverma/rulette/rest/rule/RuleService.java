package com.github.kislayverma.rulette.rest.rule;

import com.github.kislayverma.rulette.RuleSystem;
import com.github.kislayverma.rulette.core.rule.Rule;
import com.github.kislayverma.rulette.rest.rulesystem.RuleSystemFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class RuleService {
    @Autowired
    private RuleSystemFactory ruleSystemFactory;

    public List<Rule> getAllRules(String ruleSystemName) { return getRuleSystem(ruleSystemName).getAllRules(); }

    public PaginatedResult<Rule> getRules(String ruleSystemName, int pageNum, int pageSize) {
        List<Rule> allRules = getAllRules(ruleSystemName);
        int startingIndex = (pageNum - 1) * pageSize;
        if (startingIndex >= allRules.size() || startingIndex < 0) {
            return new PaginatedResult<>(null, pageNum, pageSize, allRules.size(), false, true);
        } else {
            boolean hasNext = (startingIndex + pageSize) < allRules.size() - 1;
            boolean hasPrev  = startingIndex > 0;
            return new PaginatedResult<>(
                allRules.subList(startingIndex, Math.min((startingIndex + 1) * pageSize, allRules.size() - 1)),
                pageNum,
                pageSize,
                allRules.size(),
                hasNext,
                hasPrev
            );
        }
    }

    public Rule getRuleById(String ruleSystemName, String ruleId) {
        return getRuleSystem(ruleSystemName).getRule(ruleId);
    }

    public Rule getApplicableRule(String ruleSystemName, Map<String, String> inputMap) throws Exception {
        return getRuleSystem(ruleSystemName).getRule(inputMap);
    }

    public Rule getNextApplicableRule(String ruleSystemName, Map<String, String> inputMap) throws Exception {
        return getRuleSystem(ruleSystemName).getNextApplicableRule(inputMap);
    }

    public Rule addRule(String ruleSystemName, Map<String, String> inputMap) throws Exception {
        return getRuleSystem(ruleSystemName).addRule(inputMap);
    }

    public Rule updateRule(String ruleSystemName, String ruleId, Map<String, String> inputMap) throws Exception {
        final RuleSystem rs = getRuleSystem(ruleSystemName);
        final Rule oldRule = rs.getRule(ruleId);
        Rule modifiedRule = rs.getRule(ruleId);
        for (Map.Entry<String, String> e : inputMap.entrySet()) {
            modifiedRule = modifiedRule.setColumnData(e.getKey(), e.getValue());
        }

        return rs.updateRule(oldRule, modifiedRule);
    }

    public void deleteRule(String ruleSystemName, String ruleId) throws Exception {
        getRuleSystem(ruleSystemName).deleteRule(ruleId);
    }

    public void deleteRule(String ruleSystemName, Map<String, String> inputMap) throws Exception {
        final RuleSystem rs = getRuleSystem(ruleSystemName);
        rs.deleteRule(new Rule(rs.getMetaData(), inputMap));
    }

    private RuleSystem getRuleSystem(final String ruleSystemName) {
        return ruleSystemFactory.getRuleSystem(ruleSystemName);
    }
}