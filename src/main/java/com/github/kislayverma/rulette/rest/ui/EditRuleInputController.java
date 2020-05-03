package com.github.kislayverma.rulette.rest.ui;

import com.github.kislayverma.rulette.rest.rule.RuleService;
import com.github.kislayverma.rulette.rest.ruleinput.RuleInputService;
import com.github.kislayverma.rulette.rest.rulesystem.RuleSystemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/ui")
public class EditRuleInputController {
    private static final Logger LOGGER = LoggerFactory.getLogger(EditRuleInputController.class);

    @Autowired
    private RuleSystemService ruleSystemService;

    @Autowired
    private RuleService ruleService;

    @Autowired
    private RuleInputService ruleInputService;

    @RequestMapping("/{ruleSystemName}/ruleinput/{ruleInputName}/delete")
    public String deleteRuleInput(
        Model model, @PathVariable String ruleSystemName, @PathVariable String ruleInputName, RedirectAttributes redirectAttributes) {
        ruleInputService.deleteRuleInput(ruleSystemName, ruleInputName);
        redirectAttributes.addFlashAttribute("message", "Successfully deleted ruleinput! Reload the rule system to deploy the new state");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");

        return "redirect:/ui";
    }
}
