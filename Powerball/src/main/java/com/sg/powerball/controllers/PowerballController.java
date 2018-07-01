/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.powerball.controllers;

import com.sg.powerball.models.Pick;
import com.sg.powerball.service.PowerballService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author Kyle
 */
@Controller
public class PowerballController {

    @Autowired
    private PowerballService service;

    @GetMapping("/")
    public String getIndex(Model model) {
        return "home";
    }

    @PostMapping("/draw")
    public String getDraw(Model model) {
        model.addAttribute("showResults", "false");
        return "draw";
    }

    @PostMapping("/draw/handler")
    public String makeDraw(Model model) {

        Pick drawing = service.generateQuickPickOrDrawing();

        model.addAttribute("firstNum", drawing.getFirstNum());
        model.addAttribute("secondNum", drawing.getSecondNum());
        model.addAttribute("thirdNum", drawing.getThirdNum());
        model.addAttribute("fourthNum", drawing.getFourthNum());
        model.addAttribute("fifthNum", drawing.getFifthNum());
        model.addAttribute("powerBall", drawing.getPowerBall());

        Pick closestMatch = service.findClosestMatch(drawing);

        if (closestMatch == null) {
            model.addAttribute("showResults", "showNoResultsMessage");
        } else {
            model.addAttribute("closestMatch", closestMatch);
            model.addAttribute("showResults", "true");
        }

        service.markAllAsInvalid();

        return "draw";
    }

    @PostMapping("/pick")
    public String getPick(Model model) {
        model.addAttribute("firstNum", "");
        model.addAttribute("secondNum", "");
        model.addAttribute("thirdNum", "");
        model.addAttribute("fourthNum", "");
        model.addAttribute("fifthNum", "");
        model.addAttribute("powerBall", "");

        Pick pick = new Pick();
        pick.setFirstName("");
        pick.setLastName("");
        pick.setEmail("");
        pick.setState("");

        model.addAttribute("pick", pick);

        model.addAttribute("isQuickPick", false);

        return "pick";
    }

    @PostMapping("/search")
    public String getSearch(Model model) {
        Pick pick = new Pick();
        model.addAttribute("pick", pick);
        model.addAttribute("showResults", "false");
        return "search";
    }

    @PostMapping("/search/handler")
    public String makeSearch(Pick pick, BindingResult result, Model model) {

        List<Pick> results = service.search(pick);
        model.addAttribute("pick", pick);
        model.addAttribute("results", results);
        model.addAttribute("showResults", "true");
        return "search";
    }

    @PostMapping("/pick/handler")
    public String addPick(@Valid Pick pick, BindingResult result, Model model, String action) {
        if (action.equals("quick")) {
            Pick quickPick = service.generateQuickPickOrDrawing();

            model.addAttribute("firstNum", quickPick.getFirstNum());
            model.addAttribute("secondNum", quickPick.getSecondNum());
            model.addAttribute("thirdNum", quickPick.getThirdNum());
            model.addAttribute("fourthNum", quickPick.getFourthNum());
            model.addAttribute("fifthNum", quickPick.getFifthNum());
            model.addAttribute("powerBall", quickPick.getPowerBall());

            model.addAttribute("pick", pick);

            model.addAttribute("isQuickPick", true);

            return "pick";
        } else if (action.equals("submit")) {
            if (!result.hasErrors()) {
                service.addPick(pick);
                return "home";
            } else {
                return "pick";
            }
        } else {
            return ""; // should never happen
        }
    }
}
