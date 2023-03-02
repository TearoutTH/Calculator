package com.example.calculator.controllers;

import com.example.calculator.services.CalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CalculatorController {

    @Autowired
    private CalculatorService calculatorService;

    @GetMapping("/main")
    public String getMainPage(Model model) {
        model.addAttribute("expression", "");
        return "main";
    }

    @PostMapping("/main")
    public String getMainPageWithResult(@RequestParam("expression") String expression, Model model) {
        double result;
        try {
            result = calculatorService.evaluateExpression(expression);
            model.addAttribute("result", result);
        } catch (ArithmeticException e) {
            model.addAttribute("errorMessage", "Division by zero");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Incorrect expression. Please check it's correctness and try again");
        }
        model.addAttribute("expression", expression);
        return "main";
    }
}
