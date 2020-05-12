package com.github.kislayverma.rulette.rest.ui;

import com.github.kislayverma.rulette.RuleSystem;
import com.github.kislayverma.rulette.core.metadata.RuleInputMetaData;
import com.github.kislayverma.rulette.core.metadata.RuleSystemMetaData;
import com.github.kislayverma.rulette.rest.provider.DataProviderConfig;
import com.github.kislayverma.rulette.rest.ruleinput.RuleInputDto;
import com.github.kislayverma.rulette.rest.rulesystem.RuleSystemMetadataDto;
import com.github.kislayverma.rulette.rest.rulesystem.RuleSystemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@Controller
@RequestMapping("/ui")
public class ListRuleSystemsController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ListRuleSystemsController.class);

    @Autowired
    private RuleSystemService ruleSystemService;

    @RequestMapping("")
    public String showAllRuleSystems(Model model,
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
            LOGGER.error("Error reloading rule system", e);
            redirectAttributes.addFlashAttribute("message", "Failed to reload " + ruleSystemName + " : " + e.getMessage());
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
        }

        return "redirect:/ui";
    }

    @RequestMapping("/{ruleSystemName}/delete")
    public String deleteRule(
        @PathVariable String ruleSystemName,
        RedirectAttributes redirectAttributes) {
        try {
            LOGGER.info("Deleting rule system {}", ruleSystemName);
            ruleSystemService.deleteRuleSystem(ruleSystemName);
            redirectAttributes.addFlashAttribute("message", "Successfully deleted rule system!");
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        } catch (Exception e) {
            LOGGER.error("Failed to delete rule system", e);
            redirectAttributes.addFlashAttribute("message", "Failed to delete rule system");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
        }

        return "redirect:/ui";
    }

    @RequestMapping("/rulesystem/show-add")
    public String showAddRuleSystem(Model model) {
        RuleSystemMetadataDto ruleSystemToAdd = new RuleSystemMetadataDto();
        model.addAttribute("ruleSystemToAdd", ruleSystemToAdd);

        return "add-rule-system";
    }

    @RequestMapping(value="/rulesystem/save", method= RequestMethod.POST)
    public String addRuleSystem(
        Model model,
        @ModelAttribute("ruleSystemToAdd") RuleSystemMetadataDto ruleSystemMetadataDto,
        RedirectAttributes redirectAttributes) {
        try {
            LOGGER.info(ruleSystemMetadataDto.toString());
            RuleSystemMetaData ruleSystemMetaData = new RuleSystemMetaData(
                ruleSystemMetadataDto.getRuleSystemName(),
                ruleSystemMetadataDto.getTableName(),
                ruleSystemMetadataDto.getUniqueIdColumnName(),
                ruleSystemMetadataDto.getUniqueOutputColumnName(),
                new ArrayList<>());
            ruleSystemService.createRuleSystem(ruleSystemMetaData, ruleSystemMetadataDto.getProviderName());

            redirectAttributes.addFlashAttribute("message", "Successfully added rule system! Now add rule inputs and reload");
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");

            return "redirect:/ui";
        } catch (Exception e) {
            model.addAttribute("ruleSystemToAdd", ruleSystemMetadataDto);
            model.addAttribute("message", "Error adding rule system! " + e.getMessage());
            model.addAttribute("alertClass", "alert-danger");
            LOGGER.error("Error saving new rule input", e);

            return "add-rule-system";
        }
    }
}
