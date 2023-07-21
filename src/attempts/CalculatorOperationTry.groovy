package attempts

class CalculatorOperationTry {
    String operator
    Closure<BigDecimal> calculation

    CalculatorOperationTry(String operator, Closure<BigDecimal> calculation) {
        this.operator = operator
        this.calculation = calculation
    }

    BigDecimal execute(BigDecimal... numbers) {
        return calculation(numbers)
    }

    String toString() {
        return operator
    }
}