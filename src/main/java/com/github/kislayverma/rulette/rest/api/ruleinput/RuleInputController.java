package com.github.kislayverma.rulette.rest.api.ruleinput;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.kislayverma.rulette.core.metadata.RuleInputMetaData;
import com.github.kislayverma.rulette.rest.utils.TransformerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/provider/{providerName}/rulesystem/{ruleSystemName}/ruleinput")
public class RuleInputController {
    @Autowired
    private RuleInputService ruleInputService;

    @GetMapping("/")
    public List<RuleInputMetaData> geAllRuleInputs(@PathVariable String providerName, @PathVariable String ruleSystemName) {
        return ruleInputService.getAllRuleInputs(providerName, ruleSystemName);
    }

    @GetMapping("/{ruleInputName}")
    public RuleInputMetaData getRuleInputByName(
        @PathVariable String providerName, @PathVariable String ruleSystemName, @PathVariable String ruleInputName) {
        return ruleInputService.getRuleInput(providerName, ruleSystemName, ruleInputName);
    }

    @PostMapping("/")
    public void addRuleInput(@PathVariable String providerName, @PathVariable String ruleSystemName, @RequestBody JsonNode payload) {
        RuleInputMetaData ruleInput = TransformerUtil.convertJsonToRuleInputMetadata(payload);
        ruleInputService.addRuleInput(providerName, ruleSystemName, ruleInput);
    }

    @DeleteMapping("/{ruleInputName}")
    public void deleteRuleInput(@PathVariable String providerName, @PathVariable String ruleSystemName, @PathVariable String ruleInputName) {
        ruleInputService.deleteRuleInput(providerName, ruleSystemName, ruleInputName);
    }
}
