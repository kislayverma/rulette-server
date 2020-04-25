package com.github.kislayverma.rulette.rest.rule;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.kislayverma.rulette.core.rule.Rule;
import com.github.kislayverma.rulette.rest.exception.BadServerException;
import com.github.kislayverma.rulette.rest.exception.RuleNotFoundException;
import com.github.kislayverma.rulette.rest.model.PaginatedResult;
import com.github.kislayverma.rulette.rest.rulesystem.RuleSystemService;
import com.github.kislayverma.rulette.rest.utils.TransformerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/rules/{ruleSystemName}")
public class RuleController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RuleController.class);

    @Autowired
    private RuleSystemService ruleSystemService;
    @Autowired
    private RuleService ruleService;

    @GetMapping("/")
    public PaginatedResult<Rule> getRules(@PathVariable String ruleSystemName,
                                          @RequestParam(defaultValue = "1") Integer pageNum,
                                          @RequestParam(defaultValue = "50") Integer pageSize) {
        return ruleService.getRules(ruleSystemName, pageNum, pageSize);

    }

    @GetMapping("/{ruleId}")
    public Map<String, String> getRuleById(@PathVariable String ruleSystemName, @PathVariable String ruleId) {
        return TransformerUtil.convertToRawValueMap(
            ruleSystemService.getRuleSystem(ruleSystemName), ruleService.getRuleById(ruleSystemName, ruleId));
    }

    @PostMapping("/getApplicableRule")
    public Map<String, String> getApplicableRule(@PathVariable String ruleSystemName, @RequestBody JsonNode payload) {
        try {
            return TransformerUtil.convertToRawValueMap(
                ruleSystemService.getRuleSystem(ruleSystemName),
                ruleService.getApplicableRule(ruleSystemName, TransformerUtil.convertJsonToMap(payload)));
        } catch (Exception e) {
            throw new BadServerException("Error loading rule", e);
        }
    }

    @PostMapping("/getNextApplicableRule")
    public Map<String, String> getNextApplicableRule(@PathVariable String ruleSystemName, @RequestBody JsonNode payload) {
        try {
            return TransformerUtil.convertToRawValueMap(
                ruleSystemService.getRuleSystem(ruleSystemName),
                ruleService.getNextApplicableRule(ruleSystemName, TransformerUtil.convertJsonToMap(payload)));
        } catch (Exception e) {
            throw new BadServerException("Error loading next applicable rule", e);
        }
    }

    @PostMapping("/")
    public Map<String, String> addRule(@PathVariable String ruleSystemName, @RequestBody JsonNode payload) {
        try {
            return TransformerUtil.convertToRawValueMap(
                ruleSystemService.getRuleSystem(ruleSystemName),
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
                ruleSystemService.getRuleSystem(ruleSystemName),
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
