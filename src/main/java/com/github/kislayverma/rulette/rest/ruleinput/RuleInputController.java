package com.github.kislayverma.rulette.rest.ruleinput;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.kislayverma.rulette.core.metadata.RuleInputMetaData;
import com.github.kislayverma.rulette.rest.utils.TransformerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/{ruleSystemName}/ruleinput")
public class RuleInputController {
    @Autowired
    private RuleInputService ruleInputService;

    @GetMapping("/")
    public List<RuleInputMetaData> geAlltRuleInput(@PathVariable String ruleSystemName) {
        return ruleInputService.getAllRuleInputs(ruleSystemName);
    }

    @GetMapping("/{ruleInputName}")
    public RuleInputMetaData getRuleInputByName(@PathVariable String ruleSystemName, @PathVariable String ruleInputName) {
        return ruleInputService.getRuleInput(ruleSystemName, ruleInputName);
    }

    @PostMapping("/")
    public void addRule(@PathVariable String ruleSystemName, @RequestBody JsonNode payload) {
        RuleInputMetaData ruleInput = TransformerUtil.convertJsonToRuleInputMetadata(payload);
        ruleInputService.addRuleInput(ruleSystemName, ruleInput);
    }

    @DeleteMapping("/{ruleInputName}")
    public void deleteRule(@PathVariable String ruleSystemName, @PathVariable String ruleInputName) {
        ruleInputService.deleteRuleInput(ruleSystemName, ruleInputName);
    }
}
