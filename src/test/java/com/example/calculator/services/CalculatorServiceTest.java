package com.example.calculator.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorServiceTest {
    CalculatorService calculatorService = new CalculatorService();

    @Test
    void evaluate_simpleExpressions() {
        assertEquals(0.0, calculatorService.evaluateExpression("2.5 - 2.5"));
        assertEquals(-3.0, calculatorService.evaluateExpression("-2 - 1"));
        assertEquals(2.0, calculatorService.evaluateExpression("4 / 2"));
        assertEquals(-2.0, calculatorService.evaluateExpression("-(1 + 1)"));
    }

    @Test
    void evaluate_ExpressionsWithBrackets() {
        assertEquals(1.0, calculatorService.evaluateExpression("(2 + 2) / 4"));
        assertEquals(1.75, calculatorService.evaluateExpression(" (-3 - 4) / (-4)"));
        assertEquals(-32.0, calculatorService.evaluateExpression(" ((-2 - 4) * 6) - (-4)"));
        assertEquals(-0.25, calculatorService.evaluateExpression(" (((1 - 8) + 5) + 3) / (-4)"));
    }

    @Test
    void evaluate_ExpressionsWithSqrt() {
        assertEquals(3.0, calculatorService.evaluateExpression("2 + sqrt(1)"));
        assertEquals(20.0, calculatorService.evaluateExpression("(sqrt(16) + 6) * 2"));
        assertEquals(4.0, calculatorService.evaluateExpression("2 + 2 * sqrt(1)"));
        assertEquals(6.0, calculatorService.evaluateExpression("1 + 2 * (3 + sqrt(4))-5"));
    }
}