package com.github.kislayverma.rulette.rest.rule;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.kislayverma.rulette.RuleSystem;
import com.github.kislayverma.rulette.core.rule.Rule;
import com.github.kislayverma.rulette.rest.exception.BadClientException;
import com.github.kislayverma.rulette.rest.exception.BadServerException;
import com.github.kislayverma.rulette.rest.rulesystem.RuleSystemFactory;
import com.github.kislayverma.rulette.rest.utils.TransformerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/rules/{ruleSystemName}")
public class RuleController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RuleController.class);

    @Autowired
    private RuleSystemFactory ruleSystemFactory;

    @GetMapping("/")
    public List<Map<String, String>> getAllRules(@PathVariable String ruleSystemName) {
        final RuleSystem rs = getRuleSystem(ruleSystemName);

        return TransformerUtil.convertToRawValues(rs, rs.getAllRules());
    }

    @GetMapping("/{ruleId}")
    public Map<String, String> getRuleById(@PathVariable String ruleSystemName, @PathVariable String ruleId) {
        final RuleSystem rs = getRuleSystem(ruleSystemName);

        return TransformerUtil.convertToRawValueMap(rs, rs.getRule(ruleId));
    }

    @PostMapping("/getApplicableRule")
    public Map<String, String> getApplicableRule(@PathVariable String ruleSystemName, @RequestBody JsonNode payload) {
        try {
            final RuleSystem rs = getRuleSystem(ruleSystemName);

            return TransformerUtil.convertToRawValueMap(rs, rs.getRule(TransformerUtil.convertJsonToMap(payload)));
        } catch (IOException ex) {
            throw new BadClientException("Error in parsing payload", ex);
        } catch (Exception ex) {
            throw new BadServerException("Failed to get rule", ex);
        }
    }

    @PostMapping("/getNextApplicableRule")
    public Map<String, String> getNextApplicableRule(@PathVariable String ruleSystemName, @RequestBody JsonNode payload) {
        try {
            final RuleSystem rs = getRuleSystem(ruleSystemName);

            return TransformerUtil.convertToRawValueMap(
                rs, rs.getNextApplicableRule(TransformerUtil.convertJsonToMap(payload)));
        } catch (IOException ex) {
            throw new BadClientException("Error in parsing payload", ex);
        } catch (Exception ex) {
            throw new BadServerException("Failed to get rule", ex);
        }
    }

    @PostMapping("/")
    public Map<String, String> addRule(@PathVariable String ruleSystemName, @RequestBody JsonNode payload) {
        try {
            final RuleSystem rs = getRuleSystem(ruleSystemName);

            return TransformerUtil.convertToRawValueMap(rs, rs.addRule(TransformerUtil.convertJsonToMap(payload)));
        } catch (IOException ex) {
            throw new BadClientException("Error in parsing payload", ex);
        } catch (Exception ex) {
            throw new BadServerException("Failed to get rule", ex);
        }
    }

    @PatchMapping("/{ruleId}")
    public Map<String, String> updateRule(
        @PathVariable String ruleSystemName, @PathVariable String ruleId, @RequestBody JsonNode payload) {
        try {
            final RuleSystem rs = getRuleSystem(ruleSystemName);
            final Rule oldRule = rs.getRule(ruleId);
            Rule modifiedRule = rs.getRule(ruleId);
            final Map<String, String> newDataMap = TransformerUtil.convertJsonToMap(payload);
            newDataMap.entrySet().forEach(e -> {
                try {
                    modifiedRule.setColumnData(e.getKey(), e.getValue());
                } catch (Exception ex) {
                    throw new BadServerException("Failed to update rule", ex);
                }
            });

            return TransformerUtil.convertToRawValueMap(rs, rs.updateRule(oldRule, modifiedRule));
        } catch (IOException ex) {
            throw new BadClientException("Error in parsing payload", ex);
        } catch (Exception ex) {
            throw new BadServerException("Failed to update rule", ex);
        }
    }

    @DeleteMapping("/{ruleId}")
    public void deleteRule(@PathVariable String ruleSystemName, @PathVariable String ruleId) {
        try {
            getRuleSystem(ruleSystemName).deleteRule(ruleId);
        } catch (Exception ex) {
            throw new BadServerException("Failed to get rule", ex);
        }
    }

    @DeleteMapping("/")
    public void deleteRule(@PathVariable String ruleSystemName, @RequestBody JsonNode payload) {
        try {
            final RuleSystem rs = getRuleSystem(ruleSystemName);
            rs.deleteRule(new Rule(rs.getMetaData(), TransformerUtil.convertJsonToMap(payload)));
        } catch (IOException ex) {
            throw new BadClientException("Error in parsing payload", ex);
        } catch (Exception ex) {
            throw new BadServerException("Failed to get rule", ex);
        }
    }

    private RuleSystem getRuleSystem(final String ruleSystemName) {
        return ruleSystemFactory.getRuleSystem(ruleSystemName);
    }
}
