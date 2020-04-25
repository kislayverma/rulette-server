package com.github.kislayverma.rulette.rest.rulesystem;

import com.github.kislayverma.rulette.core.metadata.RuleSystemMetaData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rulesystem")
public class RuleSystemController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RuleSystemController.class);

    @Autowired
    private RuleSystemService ruleSystemService;

    @GetMapping("/")
    public List<RuleSystemMetaData> getAllRuleSystemMetaData() {
        return ruleSystemService.getAllRuleSystemMetaData();
    }

    @GetMapping("/{ruleSystemName}")
    public RuleSystemMetaData getRuleSystemMetadata(@PathVariable String ruleSystemName) {
        return ruleSystemService.getRuleSystemMetadata(ruleSystemName);
    }

    @PutMapping("/{ruleSystemName}/reload")
    public void reload(@PathVariable String ruleSystemName) {
        ruleSystemService.reload(ruleSystemName);
    }
}
