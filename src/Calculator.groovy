class Calculator {
    static int calculate(String expression) {
        parse(expression) { a, operator, b ->
            switch (operator) {
                case '-': return a - b; break
                case '+': return a + b; break
                case '*': return a * b; break
                case '/': return a.intdiv(b); break
            }
        } as int
    }

    static def parse(expression = "test", c) {
        expression.find(/(\d+)\s*([*\/+-])\s*(\d+)/) { match, left, operator, right ->
            c.call(left.toInteger(), operator, right.toInteger())
        }
    }

}
