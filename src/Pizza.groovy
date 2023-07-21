class Pizza {
    double price

    Pizza(double prise) {
        this.price = prise
    }

    Pizza plus(double toppingsPrice) {
        return new Pizza(this.price + toppingsPrice)
    }

    Pizza plus(Pizza other) {
        return new Pizza(this.price + other.price)
    }

    def asType(Class clazz) {
        if (clazz == Double) {
            return this.price
        }
        super.asType(clazz)
    }
}