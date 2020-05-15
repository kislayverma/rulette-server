package com.github.kislayverma.rulette.rest.rulesystem;

import com.github.kislayverma.rulette.core.metadata.RuleSystemMetaData;
import com.github.kislayverma.rulette.rest.model.PaginatedResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/provider/{providerName}/rulesystem")
public class RuleSystemController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RuleSystemController.class);

    @Autowired
    private RuleSystemService ruleSystemService;

    @GetMapping("/")
    public PaginatedResult<RuleSystemMetaData> getAllRuleSystemMetaData(@RequestParam(defaultValue = "1") Integer pageNum,
                                                                        @RequestParam(defaultValue = "50") Integer pageSize) {
        return ruleSystemService.getAllRuleSystemMetaData(pageNum, pageSize);
    }

    @GetMapping("/{ruleSystemName}")
    public RuleSystemMetaData getRuleSystemMetadata(@PathVariable String providerName, @PathVariable String ruleSystemName) {
        return ruleSystemService.getRuleSystemMetadata(providerName, ruleSystemName);
    }

    @PutMapping("/{ruleSystemName}/reload")
    public void reload(@PathVariable String providerName, @PathVariable String ruleSystemName) {
        ruleSystemService.reload(providerName, ruleSystemName);
    }

    @DeleteMapping("/{ruleSystemName}")
    public void deleteRuleSystem(@PathVariable String providerName, @PathVariable String ruleSystemName) {
        ruleSystemService.deleteRuleSystem(providerName, ruleSystemName);
    }
}
