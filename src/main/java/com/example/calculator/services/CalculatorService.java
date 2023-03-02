package com.example.calculator.services;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Service;

import java.util.Stack;


@Service
public class CalculatorService {

    Stack<Double> operands = new Stack<>();
    Stack<Character> operators = new Stack<>();

    public double evaluateExpression(String expression) {
        operands.clear();
        operators.clear();
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if (c == ' ') {
                // игнор пробелов

            } else if (c >= '0' && c <= '9') {
                // обработка числа
                StringBuilder sb = new StringBuilder();
                sb.append(c);
                while (i + 1 < expression.length() && (Character.isDigit(expression.charAt(i + 1)) || expression.charAt(i + 1) == '.')) {
                    sb.append(expression.charAt(i + 1));
                    i++;
                }
                operands.push(Double.parseDouble(sb.toString()));
            } else if (c == '-') {
                // обработка унарного минуса
                if (i == 0 || expression.charAt(i - 1) == '(') {
                    operators.push('_');
                } else {
                    while (!operators.isEmpty() && !hasPrecedence(c, operators.peek())) {
                        evaluateTop(operands, operators);
                    }
                    operators.push(c);
                }
            }
            else if (c == '+' || c == '*' || c == '/') {
                // обработка операторов (кроме минуса)
                while (!operators.isEmpty() && !hasPrecedence(c, operators.peek())) {
                    evaluateTop(operands, operators);
                }
                operators.push(c);
            } else if (c == '(') {
                // обработка открывающей скобки
                operators.push(c);
            } else if (c == ')') {
                // обработка закрывающей скобки
                while (operators.peek() != '(') {
                    evaluateTop(operands, operators);
                }
                operators.pop();
            } else if (c == 's') {
                // обработка функции квадратного корня
                if (expression.substring(i, i + 4).equals("sqrt")) {
                    operators.push('s');
                    i += 3;
                }
            } else {
                throw new IllegalArgumentException();
            }
        }
        // выполнение оставшихся операций
        while (!operators.isEmpty()) {
            evaluateTop(operands, operators);
        }

        // на случай, если остались операнды (выражение введено неправильно)
        if (operands.size() != 1) {
            throw new IllegalArgumentException();
        }

        // возврат результата
        return operands.pop();
    }

    //Выполнение операции
    private static void evaluateTop(Stack<Double> operands, Stack<Character> operators) {
        char operator = operators.pop();
        if (operator == 's') {
            double operand = operands.pop();
            operands.push(Math.sqrt(operand));
        } else if (operator == '_') {
            double operand = operands.pop();
            operands.push(-operand);
        } else {
            double rightOperand = operands.pop();
            double leftOperand = operands.pop();
            switch (operator) {
                case '+' -> operands.push(leftOperand + rightOperand);
                case '-' -> operands.push(leftOperand - rightOperand);
                case '*' -> operands.push(leftOperand * rightOperand);
                case '/' ->  {
                    if (rightOperand == 0) {
                        throw new ArithmeticException();
                    }
                    operands.push(leftOperand / rightOperand);
                }
            }
        }
    }

    // Проверка на приоритет op1 над op2
    private static boolean hasPrecedence(char op1, char op2) {
        if (op2 == '(' || op2 == ')') {
            return true;
        }
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-')) {
            return true;
        }
        return false;
    }
}
