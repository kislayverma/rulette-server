package com.github.kislayverma.rulette.rest.ruleinput;

import com.github.kislayverma.rulette.core.metadata.RuleInputMetaData;
import com.github.kislayverma.rulette.rest.exception.RuleNotFoundException;
import com.github.kislayverma.rulette.rest.rulesystem.RuleSystemFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RuleInputService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RuleInputService.class);

    @Autowired
    private RuleSystemFactory ruleSystemFactory;

    public List<RuleInputMetaData> getAllRuleInputs(final String ruleSystemName) {
        return ruleSystemFactory.getRuleSystem(ruleSystemName).getMetaData().getInputColumnList();
    }

    public RuleInputMetaData getRuleInput(final String ruleSystemName, final String ruleInputName) {
        List<RuleInputMetaData> ruleInputs =
            ruleSystemFactory.getRuleSystem(ruleSystemName).getMetaData().getInputColumnList();
        return ruleInputs.stream().filter(ruleInput -> ruleInput.getName().equals(ruleInputName)).findFirst()
            .orElseThrow(() -> new RuleNotFoundException("No rule input with name " + ruleSystemName));
    }

    public void addRuleInput(final String ruleSystemName, final RuleInputMetaData ruleInput) {
        LOGGER.info("Adding new rule input to rule system {} : {}", ruleSystemName, ruleInput);
        ruleSystemFactory.getRuleSystem(ruleSystemName).addRuleInput(ruleInput);
    }

    public void deleteRuleInput(final String ruleSystemName, final String ruleInputName) {
        LOGGER.info("Deleting rule input {} from rule system {}", ruleInputName, ruleSystemName);
        ruleSystemFactory.getRuleSystem(ruleSystemName).deleteRuleInput(ruleInputName);
    }
}
