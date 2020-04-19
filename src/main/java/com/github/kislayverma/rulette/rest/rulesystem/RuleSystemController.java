package com.github.kislayverma.rulette.rest.rulesystem;

import com.github.kislayverma.rulette.RuleSystem;
import com.github.kislayverma.rulette.core.metadata.RuleSystemMetaData;
import com.github.kislayverma.rulette.rest.exception.BadServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/rulesystem")
public class RuleSystemController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RuleSystemController.class);

    @Autowired
    private RuleSystemFactory ruleSystemFactory;

    @GetMapping("/")
    public List<RuleSystemMetaData> getAllRuleSystemMetaData() {
        final List<RuleSystemMetaData> output = new ArrayList<>();
        ruleSystemFactory.getAllRuleSystems().forEach(rs -> {
            try {
                output.add(rs.getMetaData());
            } catch (Exception ex) {
                throw new BadServerException("Error returning rule system metadata", ex);
            }
        });

        return output;
    }

    @GetMapping("/{ruleSystemName}")
    public RuleSystemMetaData getRuleSystemMetadata(@PathVariable String ruleSystemName) {
        try {
            return getRuleSystem(ruleSystemName).getMetaData();
        } catch (Exception ex) {
            throw new BadServerException("Error returning rule system metadata", ex);
        }
    }

    @PutMapping("/{ruleSystemName}/reload")
    public void reload(@PathVariable String ruleSystemName) {
        try {
            ruleSystemFactory.reloadRuleSystem(ruleSystemName);
        } catch (Exception ex) {
            throw new BadServerException("Error returning rule system metadata", ex);
        }
    }

    private RuleSystem getRuleSystem(final String ruleSystemName) {
        return ruleSystemFactory.getRuleSystem(ruleSystemName);
    }
}
