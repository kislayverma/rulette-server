package com.github.kislayverma.rulette.rest.ui;

import com.github.kislayverma.rulette.rest.rulesystem.RuleSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/ui")
public class ListRuleSystemsController {

    @Autowired
    private RuleSystemService ruleSystemService;

    @RequestMapping("")
    public String showAllRuleSystem(Model model,
                                    @RequestParam(defaultValue = "1") Integer pageNum,
                                    @RequestParam(defaultValue = "50") Integer pageSize) {
        model.addAttribute("ruleSystemPage", ruleSystemService.getAllRuleSystemMetaData(pageNum, pageSize));
        return "rule-systems";
    }
}
