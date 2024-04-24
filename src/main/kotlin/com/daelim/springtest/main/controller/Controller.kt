import kotlin.random.Random
class Controller {
    private val tests = mutableListOf<TestDto>()

    fun RandomLotto() {
        val numberOfRows = 5
        val numbersPerRow = 7
        val minNumber = 1
        val maxNumber = 45

        val rows = Array(numberOfRows) { IntArray(numbersPerRow) }

        for (i in rows.indices) {
            val generatedNumbers = mutableSetOf<Int>()
            while (generatedNumbers.size < numbersPerRow) {
                val randomNumber = Random.nextInt(minNumber, maxNumber + 1)
                generatedNumbers.add(randomNumber)
            }
            rows[i] = generatedNumbers.toIntArray().sorted().toIntArray()
        }
    }
}
