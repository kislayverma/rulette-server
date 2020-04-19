package com.github.kislayverma.rulette.rest.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kislayverma.rulette.RuleSystem;
import com.github.kislayverma.rulette.core.rule.Rule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransformerUtil {
    public static Map<String, String> convertToRawValueMap(final RuleSystem ruleSystem, final Rule rule) {
        if (ruleSystem == null || rule == null) {
            throw new IllegalArgumentException("Invalid input in transforming rule to rule DTO");
        }
        final Map<String, String> ruleValues = new HashMap<>();
        ruleSystem.getAllColumnNames()
            .forEach(columnName -> {
                ruleValues.put(columnName, rule.getColumnData(columnName).getRawValue());
            });

        return ruleValues;
    }

    public static List<Map<String, String>> convertToRawValues(final RuleSystem ruleSystem, final List<Rule> rules) {
        if (ruleSystem == null || rules == null) {
            throw new IllegalArgumentException("Invalid input in transforming rule to rule DTO");
        }

        final List<Map<String, String>> output = new ArrayList<>();
        rules.forEach(rule -> output.add(convertToRawValueMap(ruleSystem, rule)));

        return output;
    }

    public static Map<String, String> convertJsonToMap(JsonNode criteria) {
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
