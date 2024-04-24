package com.daelim.springtest.main.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class LottoController {

    private val generatedNumbers = mutableListOf<List<Int>>()
    private var clickCount = 0

    @PostMapping("/generate")
    fun generateNumbers(): ResponseEntity<String> {
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
        return ResponseEntity.ok().body("${clickCount}회차")
    }

    @GetMapping("/numbers")
    fun getGeneratedNumbers(): ResponseEntity<List<List<Int>>> {
        return ResponseEntity.ok().body(generatedNumbers)
    }
}
