package com.github.kislayverma.rulette.rest.ruleinput;

import com.github.kislayverma.rulette.core.metadata.RuleInputMetaData;
import com.github.kislayverma.rulette.rest.exception.RuleNotFoundException;
import com.github.kislayverma.rulette.rest.rulesystem.RuleSystemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RuleInputService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RuleInputService.class);

    @Autowired
    private RuleSystemService ruleSystemService;

    public List<RuleInputMetaData> getAllRuleInputs(final String providerName, final String ruleSystemName) {
        return ruleSystemService.getRuleSystem(providerName, ruleSystemName).getMetaData().getInputColumnList();
    }

    public RuleInputMetaData getRuleInput(final String providerName, final String ruleSystemName, final String ruleInputName) {
        List<RuleInputMetaData> ruleInputs =
            ruleSystemService.getRuleSystem(providerName, ruleSystemName).getMetaData().getInputColumnList();
        return ruleInputs.stream().filter(ruleInput -> ruleInput.getName().equals(ruleInputName)).findFirst()
            .orElseThrow(() -> new RuleNotFoundException("No rule input with name " + ruleSystemName));
    }

    public void addRuleInput(final String providerName, final String ruleSystemName, final RuleInputMetaData ruleInput) {
        LOGGER.info("Adding new rule input to rule system {} : {}", ruleSystemName, ruleInput);
        ruleSystemService.getRuleSystem(providerName, ruleSystemName).addRuleInput(ruleInput);
    }

    public void deleteRuleInput(final String providerName, final String ruleSystemName, final String ruleInputName) {
        LOGGER.info("Deleting rule input {} from rule system {}", ruleInputName, ruleSystemName);
        ruleSystemService.getRuleSystem(providerName, ruleSystemName).deleteRuleInput(ruleInputName);
    }
}
