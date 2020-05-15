package com.github.kislayverma.rulette.rest.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kislayverma.rulette.RuleSystem;
import com.github.kislayverma.rulette.core.metadata.RuleInputMetaData;
import com.github.kislayverma.rulette.core.metadata.RuleSystemMetaData;
import com.github.kislayverma.rulette.core.rule.Rule;
import com.github.kislayverma.rulette.core.ruleinput.type.RuleInputType;
import com.github.kislayverma.rulette.rest.exception.RuleNotFoundException;
import com.github.kislayverma.rulette.rest.exception.RuleSystemNotFoundException;
import com.github.kislayverma.rulette.rest.rulesystem.RuleSystemMetadataDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransformerUtil {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static Map<String, String> convertToRawValueMap(final RuleSystem ruleSystem, final Rule rule) {
        if (ruleSystem == null) {
            throw new RuleSystemNotFoundException("No rule system defined for transforming rule to Rule DTO");
        } else if (rule == null) {
            throw new RuleNotFoundException("No rule defined for transforming to DTO");
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

    public static RuleInputMetaData convertJsonToRuleInputMetadata(JsonNode jsonData) {
        Map<String, String> intputMap = convertJsonToMap(jsonData);
        String name = intputMap.get("name");
        int priority= Integer.parseInt(intputMap.get("priority"));
        RuleInputType inputType = RuleInputType.valueOf(intputMap.get("ruleInputType"));
        String dataType = intputMap.get("dataType");
        String rangeLowerBoundFieldName = intputMap.get("rangeLowerBoundFieldName");
        String rangeUpperBoundFieldName = intputMap.get("rangeUpperBoundFieldName");

        return new RuleInputMetaData(
            name, priority, inputType, dataType, rangeLowerBoundFieldName, rangeUpperBoundFieldName);
    }

    public static Map<String, String> convertJsonToMap(JsonNode jsonData) {
        Map<String, String> map = new HashMap<>();
        Map<String, Object> objMap =
            OBJECT_MAPPER.convertValue(jsonData, new TypeReference<Map<String, Object>>(){});

        objMap.entrySet()
            .stream()
            .forEach(entry -> {
                if (entry.getValue() != null) {
                    map.put(entry.getKey(), (String) entry.getValue().toString());
                }
            });

        return map;
    }

    public static RuleSystemMetadataDto transformToDto(RuleSystemMetaData rsmd, String providerName) {
        RuleSystemMetadataDto dto = new RuleSystemMetadataDto();
        dto.setRuleSystemName(rsmd.getRuleSystemName());
        dto.setProviderName(providerName);
        dto.setInputColumnList(rsmd.getInputColumnList());
        dto.setTableName(rsmd.getTableName());
        dto.setUniqueIdColumnName(rsmd.getUniqueIdColumnName());
        dto.setUniqueOutputColumnName(rsmd.getUniqueOutputColumnName());

        return dto;
    }
}
