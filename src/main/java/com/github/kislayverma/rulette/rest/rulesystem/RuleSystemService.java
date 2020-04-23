package com.github.kislayverma.rulette.rest.rulesystem;

import com.github.kislayverma.rulette.RuleSystem;
import com.github.kislayverma.rulette.core.metadata.RuleSystemMetaData;
import com.github.kislayverma.rulette.rest.exception.BadServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RuleSystemService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RuleSystemService.class);

    @Autowired
    private RuleSystemFactory ruleSystemFactory;

    public RuleSystem getRuleSystem(final String ruleSystemName) {
        return ruleSystemFactory.getRuleSystem(ruleSystemName);
    }

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

    public RuleSystemMetaData getRuleSystemMetadata(String ruleSystemName) {
        try {
            return getRuleSystem(ruleSystemName).getMetaData();
        } catch (Exception ex) {
            throw new BadServerException("Error returning rule system metadata", ex);
        }
    }

    public void reload(String ruleSystemName) {
        try {
            ruleSystemFactory.reloadRuleSystem(ruleSystemName);
        } catch (Exception ex) {
            throw new BadServerException("Error returning rule system metadata", ex);
        }
    }
}
