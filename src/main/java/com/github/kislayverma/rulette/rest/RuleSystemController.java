package com.github.kislayverma.rulette.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kislayverma.rulette.RuleSystem;
import com.github.kislayverma.rulette.core.rule.Rule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/rs")
public class RuleSystemController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RuleSystemController.class);

    @Autowired
    private RuleSystemFactory ruleSystemFactory;

    @GetMapping("/{ruleSystemName}")
    public List<Rule> getAllRules(@PathVariable String ruleSystemName) {
        return getRuleSystem(ruleSystemName).getAllRules();
    }

    @PostMapping("/{ruleSystemName}/getRule")
    public Rule getApplicableRule(@PathVariable String ruleSystemName,@RequestBody JsonNode payload) {
        try {
            Map<String, String> map = convertInputJsonToMap(payload);
            return getRuleSystem(ruleSystemName).getRule(map);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    private RuleSystem getRuleSystem(String ruleSystemName) {
        return ruleSystemFactory.getRuleSystem(ruleSystemName);
    }

    private Map<String, String> convertInputJsonToMap(JsonNode criteria) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        Map<String, String> map = new HashMap<>();
        Map<String, Object> objMap =
            mapper.convertValue(criteria, new TypeReference<Map<String, Object>>(){});

        objMap.entrySet().stream().forEach((entry) -> {
            map.put(entry.getKey(), (String) entry.getValue());
        });

        return map;
    }
}
