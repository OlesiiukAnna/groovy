package attempts

import attempts.CalculatorOperationTry

class CalculatorTry {
    List<CalculatorOperationTry> operations = []

    CalculatorTry() {
        operations.add(new CalculatorOperationTry("+", {
            numbers -> numbers.inject(BigDecimal.ZERO) { sum, num -> sum + num } }))

        operations.add(new CalculatorOperationTry("-", {
            numbers -> numbers[0] - numbers[1..-1].inject(BigDecimal.ZERO) { diff, num -> diff + num } }))

        operations.add(new CalculatorOperationTry("*", {
            numbers -> numbers.inject(BigDecimal.ONE) { product, num -> product * num } }))

        operations.add(new CalculatorOperationTry("/", {
            numbers -> numbers[0] / numbers[1..-1].inject(BigDecimal.ONE) { quotient, num -> quotient * num } }))
    }

    BigDecimal calculate(String expression) {
        def transformedExpression = transformExpression(expression)
        def postfixExpression = infixToPostfix(transformedExpression)

        def stack = []
        def parts = postfixExpression.tokenize()

        parts.each { part ->
            if (isOperator(part)) {
                def operation = operations.find { it.operator == part }
                def arguments = []

                (0..<operation.calculation.maximumNumberOfParameters).each {
                    arguments << stack.pop()
                }

                def result = operation.execute(arguments.reverse())
                stack.push(result)
            } else {
                stack.push(new BigDecimal(part))
            }
        }

        return stack.pop()
    }

    String transformExpression(String expression) {
        // Add spaces around operators and parentheses to separate tokens
        expression = expression.replaceAll(/([\+\-\*\/\(\)])/) { " $it " }

        // Add spaces around parentheses inside the expression
        expression = expression.replaceAll(/\(/, "( ")
        expression = expression.replaceAll(/\)/, " )")

        return expression.trim()
    }

    String infixToPostfix(String expression) {
        def outputQueue = []
        def operatorStack = []

        def parts = expression.tokenize()

        parts.each { part ->
            if (isOperator(part)) {
                def operator1 = part
                def operator2 = operatorStack.last()

                while (isOperator(operator2) && hasHigherPrecedence(operator1, operator2)) {
                    outputQueue << operatorStack.pop()
                    operator2 = operatorStack.last()
                }

                operatorStack << operator1
            } else if (part == "(") {
                operatorStack << part
            } else if (part == ")") {
                def operator = operatorStack.pop()

                while (operator != "(") {
                    outputQueue << operator
                    operator = operatorStack.pop()
                }
            } else {
                outputQueue << part
            }
        }

        while (!operatorStack.isEmpty()) {
            outputQueue << operatorStack.pop()
        }

        return outputQueue.join(" ")
    }

    boolean isOperator(String token) {
        return operations.find { it.operator == token } != null
    }

    boolean hasHigherPrecedence(String op1, String op2) {
        return getOperatorPrecedence(op1) >= getOperatorPrecedence(op2)
    }

    int getOperatorPrecedence(String operator) {
        if (operator == "+" || operator == "-") {
            return 1
        } else if (operator == "*" || operator == "/") {
            return 2
        } else {
            return 0
        }
    }

}
