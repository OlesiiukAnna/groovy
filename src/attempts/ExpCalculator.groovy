package attempts

def evaluateExpression(expression) {
    expression = expression.replaceAll('\\s+', '')
    def index = 0
    def length = expression.length()

    def parseOperand = {
        def start = index
        while (index < length && Character.isDigit(expression.charAt(index))) {
            index++
        }
        Integer.parseInt(expression.substring(start, index))
    }

    def parseOperator = {
        if (index < length && "+-*/".contains(String.valueOf(expression.charAt(index)))) {
            def operator = expression.charAt(index)
            index++
            operator
        } else {
            null
        }
    }

    def hasPrecedence = { op1, op2 ->
        (op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-')
    }

    def applyOperator = { operand1, operand2, operator ->
        switch (operator) {
            case '+':
                operand1 + operand2
                break
            case '-':
                operand1 - operand2
                break
            case '*':
                operand1 * operand2
                break
            case '/':
                operand1.toDouble() / operand2
                break
            default:
                throw new IllegalArgumentException("Invalid operator: $operator")
        }
    }

    def evaluate = {
        def operand1 = parseOperand
        def operator = parseOperator
        def operand2 = parseOperand

        if (index < length && operator != null && operand2 != null) {
            def nextOperator = parseOperator()

            if (nextOperator != null && hasPrecedence(nextOperator, operator)) {
                operand2 = evaluate()
            }

            operand1 = applyOperator(operand1, operand2, operator)
            operator = nextOperator
        }

        operand1
    }

    evaluate()


}
