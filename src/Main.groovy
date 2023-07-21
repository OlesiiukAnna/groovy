static void main(String[] args) {

    def calculator = new Calculator()

    assert calculator.calculate('4 + 2 + 2') == 6
//    assert calculator.calculate('4 + 3') == 7
//    assert calculator.calculate('10 + 3') == 13
//    assert calculator.calculate('3 + 10') == 13
//    assert calculator.calculate('103 + 210') == 313
//    assert calculator.calculate('1250005 + 1210') == 1251215
//    assert calculator.calculate('1+ 1') == 2
//    assert calculator.calculate('1 +1') == 2
//    assert calculator.calculate('1+1') == 2
//    assert calculator.calculate('1    +          1') == 2
//    assert calculator.calculate('2 - 2') == 0
//    assert calculator.calculate('2 * 2') == 4
//    assert calculator.calculate('2 / 2') == 1
    try {
        calculator.calculate('1/0')
        throw new IllegalArgumentException("Division by zero not caught")
    } catch (DivisionByZeroError) {
    }
    assert calculator.calculate('2 / 4') == 0

    //----------------------------------------------------------------------

    def pizza1 = new Pizza(4)
    def pizza2 = new Pizza(11)

    assert (pizza1 + pizza2).price == 15
    assert (pizza1 + 11).price == 15

    def price = pizza1 as Double
    println price

    println pizza1?.getPrice() + 5.0
    println new Pizza(-2.0)?.getPrice() + 5.0

    println("Initial price: ${pizza1.price}")

    pizza1.price += 3.0
    println("Updated price: ${pizza1.price}")

    //----------------------------------------------------------------------

    def igor = new Person(name:'Igor')
    def closure1 = { name.toUpperCase() }
    closure1.delegate = igor
    assert closure1() == 'IGOR'

    def person = new Person(name:'Jessica', age:42)
    def thing = new Thing(name:'Printer')
    def closure2 = person.fetchAge
    closure2.resolveStrategy = Closure.DELEGATE_FIRST
    closure2.delegate = person
    assert closure2() == 42
    closure2.delegate = thing
    assert closure2() == -1

}
