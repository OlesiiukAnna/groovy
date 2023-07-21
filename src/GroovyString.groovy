class GroovyString {

    static void main(String[] args) {
        intro()
        triplequotes()
        callNameWithTheDot()
        slashyString()
        chars()
    }

    private static void intro() {
        def name = "Kacper"
        def result = "Hello ${name}!"
        def result1 = 'Hello ${name}!'

        println(result)
        println "Hello Kacper!" == result

        println(result1)
        println result1 - ("name")

        def hardToRead = "Kacper loves \"Lord of the Rings\""
        def easyToRead = 'Kacper loves "Lord of the Rings"'

        println hardToRead
        println easyToRead
    }

    private static void triplequotes() {
        def name = "John"
        def jsonContent = """\
        {
            "name": "$name",
            |"age": 20,
            "birthDate": null
        }""".stripIndent().stripMargin()
        println jsonContent
    }

    private static void callNameWithTheDot() {
        given:
        def person = [name: 'John']

        when:
        def myNameIs = "I'm $person.name, and you?".toString()

        then:
        println myNameIs == "I'm John, and you?"
    }

    private static void slashyString() {
        def slashyString = $/ \/ $ + - * / /$
        println(slashyString)

        def pattern = /\d{3}/
        println '123'.matches(pattern)
    }

    private static void chars() {
        given:
        def a = 'A' as char
        def b = 'B' as char
        def c = (char) 'C'

        expect:
        println(a instanceof Character)
        println(b instanceof Character)
        println(c instanceof Character)
    }

}
