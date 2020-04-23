package com.github.kislayverma.rulette.rest.rule;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.kislayverma.rulette.rest.exception.BadServerException;
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
    private RuleService ruleService;

    @GetMapping("/")
    public List<Map<String, String>> getAllRules(@PathVariable String ruleSystemName) {
        return TransformerUtil.convertToRawValues(
            ruleService.getRuleSystem(ruleSystemName), ruleService.getAllRules(ruleSystemName));
    }

    @GetMapping("/{ruleId}")
    public Map<String, String> getRuleById(@PathVariable String ruleSystemName, @PathVariable String ruleId) {
        return TransformerUtil.convertToRawValueMap(
            ruleService.getRuleSystem(ruleSystemName), ruleService.getRuleById(ruleSystemName, ruleId));
    }

    @PostMapping("/getApplicableRule")
    public Map<String, String> getApplicableRule(@PathVariable String ruleSystemName, @RequestBody JsonNode payload) {
        try {
            return TransformerUtil.convertToRawValueMap(
                ruleService.getRuleSystem(ruleSystemName),
                ruleService.getApplicableRule(ruleSystemName, TransformerUtil.convertJsonToMap(payload)));
        } catch (Exception e) {
            throw new BadServerException("Error loading rule", e);
        }
    }

    @PostMapping("/getNextApplicableRule")
    public Map<String, String> getNextApplicableRule(@PathVariable String ruleSystemName, @RequestBody JsonNode payload) {
        try {
            return TransformerUtil.convertToRawValueMap(
                ruleService.getRuleSystem(ruleSystemName),
                ruleService.getNextApplicableRule(ruleSystemName, TransformerUtil.convertJsonToMap(payload)));
        } catch (Exception e) {
            throw new BadServerException("Error loading next applicable rule", e);
        }
    }

    @PostMapping("/")
    public Map<String, String> addRule(@PathVariable String ruleSystemName, @RequestBody JsonNode payload) {
        try {
            return TransformerUtil.convertToRawValueMap(
                ruleService.getRuleSystem(ruleSystemName),
                ruleService.addRule(ruleSystemName, TransformerUtil.convertJsonToMap(payload)));
        } catch (Exception ex) {
            throw new BadServerException("Failed to add rule", ex);
        }
    }

    @PatchMapping("/{ruleId}")
    public Map<String, String> updateRule(
        @PathVariable String ruleSystemName, @PathVariable String ruleId, @RequestBody JsonNode payload) {
        try {
            return TransformerUtil.convertToRawValueMap(
                ruleService.getRuleSystem(ruleSystemName),
                ruleService.updateRule(ruleSystemName, ruleId, TransformerUtil.convertJsonToMap(payload)));
        } catch (Exception ex) {
            throw new BadServerException("Failed to update rule", ex);
        }
    }

    @DeleteMapping("/{ruleId}")
    public void deleteRule(@PathVariable String ruleSystemName, @PathVariable String ruleId) {
        try {
            ruleService.deleteRule(ruleSystemName, ruleId);
        } catch (Exception ex) {
            throw new BadServerException("Failed to delete rule", ex);
        }
    }

    @DeleteMapping("/")
    public void deleteRule(@PathVariable String ruleSystemName, @RequestBody JsonNode payload) {
        try {
            ruleService.deleteRule(ruleSystemName, TransformerUtil.convertJsonToMap(payload));
        } catch (Exception ex) {
            throw new BadServerException("Failed to delete rule", ex);
        }
    }
}
