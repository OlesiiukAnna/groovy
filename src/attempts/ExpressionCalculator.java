package attempts;

import java.util.*;

public class ExpressionCalculator {
    public static void main(String[] args) {
        int result = evaluateExpression("2*2+5-2*3");
//        int result = evaluateExpression("2+6*7-6/2+30/5");
        System.out.println("Result: " + result);
    }

    public static int evaluateExpression(String expression) {
        // Remove all whitespaces from the expression
        expression = expression.replaceAll("\\s+", "");

        // Convert the infix expression to postfix (RPN) notation
        List<String> postfixExpression = convertToPostfix(expression);

        // Evaluate the postfix expression
        return evaluatePostfix(postfixExpression);
    }

    public static List<String> convertToPostfix(String expression) {
        List<String> postfixExpression = new ArrayList<>();
        Deque<Character> operatorStack = new ArrayDeque<>();

        Map<Character, Integer> precedence = new HashMap<>();
        precedence.put('(', 0);
        precedence.put('+', 1);
        precedence.put('-', 1);
        precedence.put('*', 2);
        precedence.put('/', 2);

        int index = 0;
        while (index < expression.length()) {
            char currentChar = expression.charAt(index);

            if (Character.isDigit(currentChar)) {
                StringBuilder sb = new StringBuilder();

                // Read the entire number
                while (index < expression.length() && Character.isDigit(expression.charAt(index))) {
                    sb.append(expression.charAt(index));
                    index++;
                }

                postfixExpression.add(sb.toString());
            } else if (currentChar == '(') {
                operatorStack.push(currentChar);
                index++;
            } else if (currentChar == ')') {
                while (!operatorStack.isEmpty() && operatorStack.peek() != '(') {
                    postfixExpression.add(operatorStack.pop().toString());
                }

                if (!operatorStack.isEmpty() && operatorStack.peek() == '(') {
                    operatorStack.pop();
                }

                index++;
            } else {
                while (!operatorStack.isEmpty() && precedence.get(currentChar) <= precedence.getOrDefault(operatorStack.peek(), 0)) {
                    postfixExpression.add(operatorStack.pop().toString());
                }

                operatorStack.push(currentChar);
                index++;
            }
        }

        while (!operatorStack.isEmpty()) {
            postfixExpression.add(operatorStack.pop().toString());
        }

        return postfixExpression;
    }

    public static int evaluatePostfix(List<String> postfixExpression) {
        Deque<Integer> operandStack = new ArrayDeque<>();

        for (String token : postfixExpression) {
            if (isNumeric(token)) {
                operandStack.push(Integer.parseInt(token));
            } else {
                int num2 = operandStack.pop();
                int num1 = operandStack.pop();

                int result = applyOperator(num1, num2, token);
                operandStack.push(result);
            }
        }

        return operandStack.pop();
    }

    public static int applyOperator(int num1, int num2, String operator) {
        switch (operator) {
            case "+":
                return num1 + num2;
            case "-":
                return num1 - num2;
            case "*":
                return num1 * num2;
            case "/":
                return num1 / num2;
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }

    public static boolean isNumeric(String token) {
        try {
            Integer.parseInt(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
