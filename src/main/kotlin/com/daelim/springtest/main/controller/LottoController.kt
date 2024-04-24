package com.daelim.springtest.main.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class LottoController {

    data class WinningNumbers(
        val numbers: List<Int>,
        val bonusNumber: Int?
    )

    data class LottoResult(
        val index: Int,
        val winningNumbers: WinningNumbers,
        val results: List<LottoCheckResult>
    )

    data class LottoCheckResult(
        val numbers: List<Int>,
        val correctNumbers: WinningNumbers,
        val result: String
    )

    private val generatedNumbers = mutableListOf<List<Int>>()

    private var clickCount = 0

    @PostMapping("/lotto")
    fun generateNumbers(): ResponseEntity<Map<String, String>> {
        val numbersPerRow = 7
        val minNumber = 1
        val maxNumber = 45

        val generatedRow = mutableListOf<Int>()
        while (generatedRow.size < numbersPerRow) {
            val randomNumber = (minNumber..maxNumber).random()
            if (!generatedRow.contains(randomNumber)) {
                generatedRow.add(randomNumber)
            }
        }
        generatedNumbers.add(generatedRow.sorted())
        clickCount++
        return ResponseEntity.ok().body(mapOf("message" to "${clickCount}회차"))
    }

    @GetMapping("/lotto")
    fun getGeneratedNumbers(): ResponseEntity<List<List<Int>>> {
        return ResponseEntity.ok().body(generatedNumbers)
    }

    @GetMapping("/lotto/check")
    fun checkLottoResults(): ResponseEntity<List<LottoResult>> {
        val lastGeneratedNumbers = generatedNumbers.lastOrNull()
        if (lastGeneratedNumbers.isNullOrEmpty()) {
            return ResponseEntity.ok().body(emptyList())
        }

        val winningNumbers = WinningNumbers(
            numbers = lastGeneratedNumbers,
            bonusNumber = lastGeneratedNumbers[6]
        )

        val results = mutableListOf<LottoResult>()
        generatedNumbers.forEachIndexed { index, numbers ->
            val correctNumbers = mutableListOf<Int>()
            numbers.forEach { number ->
                if (winningNumbers.numbers.contains(number)) {
                    correctNumbers.add(number)
                }
            }
            val bonusNumberMatched = winningNumbers.bonusNumber != null && numbers.contains(winningNumbers.bonusNumber)
            val lottoCheckResult = LottoCheckResult(
                numbers = numbers,
                correctNumbers = WinningNumbers(correctNumbers, if (bonusNumberMatched) winningNumbers.bonusNumber else null),
                result = if (correctNumbers.size == 6) "1등 당첨" else if (correctNumbers.size == 5 && bonusNumberMatched) "2등 당첨" else "낙첨"
            )
            results.add(LottoResult(index + 1, winningNumbers, listOf(lottoCheckResult)))
        }
        return ResponseEntity.ok().body(results)
    }
}
