package com.github.kislayverma.rulette.rest.ui;

import com.github.kislayverma.rulette.core.metadata.RuleSystemMetaData;
import com.github.kislayverma.rulette.rest.rulesystem.RuleSystemController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private RuleSystemController ruleSystemController;

    @GetMapping("/")
    public String showAllRuleSystem(Model model) {
        model.addAttribute("ruleSystems", ruleSystemController.getAllRuleSystemMetaData());
        return "rule-systems";
    }
}
