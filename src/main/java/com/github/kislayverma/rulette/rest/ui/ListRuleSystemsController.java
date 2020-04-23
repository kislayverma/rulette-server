package com.github.kislayverma.rulette.rest.ui;

import com.github.kislayverma.rulette.rest.rulesystem.RuleSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ui")
public class ListRuleSystemsController {

    @Autowired
    private RuleSystemService ruleSystemService;

    @RequestMapping("/")
    public String showAllRuleSystem(Model model) {
        model.addAttribute("ruleSystems", ruleSystemService.getAllRuleSystemMetaData());
        return "rule-systems";
    }
}
