package com.github.kislayverma.rulette.rest.ui;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @RequestMapping("/")
    public String showHome(Model model) {
        return "redirect:/ui/";
    }
}
