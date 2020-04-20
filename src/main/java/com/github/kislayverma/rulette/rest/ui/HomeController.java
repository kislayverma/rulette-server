package com.github.kislayverma.rulette.rest.ui;

import com.github.kislayverma.rulette.core.metadata.RuleSystemMetaData;
import com.github.kislayverma.rulette.rest.rule.RuleController;
import com.github.kislayverma.rulette.rest.rulesystem.RuleSystemController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/ui")
public class HomeController {
    @Autowired
    private RuleSystemController ruleSystemController;

    @Autowired
    private RuleController ruleController;

    @GetMapping("/")
    public String showAllRuleSystem(Model model) {
        model.addAttribute("ruleSystems", ruleSystemController.getAllRuleSystemMetaData());
        return "rule-systems";
    }

    @GetMapping("/{ruleSystemName}/rules")
    public String showAllRulesForRuleSystem(Model model, @PathVariable String ruleSystemName) {
        model.addAttribute("ruleSystem", ruleSystemController.getRuleSystemMetadata(ruleSystemName));
        model.addAttribute("rules", ruleController.getAllRules(ruleSystemName));

        return "rules";
    }
}
