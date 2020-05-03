package com.github.kislayverma.rulette.rest.ui;

import com.github.kislayverma.rulette.rest.rulesystem.RuleSystemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/ui")
public class ListRuleSystemsController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ListRuleSystemsController.class);

    @Autowired
    private RuleSystemService ruleSystemService;

    @RequestMapping("")
    public String showAllRuleSystem(Model model,
                                    @RequestParam(defaultValue = "1") Integer pageNum,
                                    @RequestParam(defaultValue = "50") Integer pageSize) {
        model.addAttribute("ruleSystemPage", ruleSystemService.getAllRuleSystemMetaData(pageNum, pageSize));
        return "rule-systems";
    }

    @RequestMapping("/{ruleSystemName}/reload")
    public String reloadRuleSystem(Model model, @PathVariable String ruleSystemName, RedirectAttributes redirectAttributes) {
        try {
            LOGGER.info("Reloading rule system {}", ruleSystemName);
            ruleSystemService.reload(ruleSystemName);
            redirectAttributes.addFlashAttribute("message", "Successfully reloaded " + ruleSystemName);
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Failed to reload " + ruleSystemName + " : " + e.getMessage());
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
        }

        return "redirect:/ui";
    }
}
