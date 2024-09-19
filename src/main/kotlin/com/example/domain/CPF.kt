package com.example.domain

import kotlinx.serialization.Serializable

const val CPF_VALID_LENGTH = 11
const val FIRST_DIGIT_FACTOR = 10
const val SECOND_DIGIT_FACTOR = 11

@Serializable
data class CPF(val value: String) {
    init {
        if (!validate()) {
            throw Exception("Invalid cpf")
        }
    }

    private fun allDigitsAreEqual(cpf: String): Boolean {
        return cpf.all { it == cpf[0] }
    }

    private fun calculateDigit(cpf: String, pFactor: Int): Int {
        var total = 0
        var factor = pFactor
        for (digit in cpf) {
            if (factor > 1) total += digit.toString().toInt() * factor--
        }
        val remainder = total % 11
        return if (remainder < 2) 0 else 11 - remainder
    }

    private fun extractDigit(cpf: String): String {
        return cpf.slice(9 until cpf.length)
    }

    private fun validate(): Boolean {
        val cpfDigits = value.filter { it.isDigit() }

        if (cpfDigits.length != CPF_VALID_LENGTH) return false
        if (allDigitsAreEqual(cpfDigits)) return false
        val digit1 = calculateDigit(cpfDigits, FIRST_DIGIT_FACTOR)
        val digit2 = calculateDigit(cpfDigits, SECOND_DIGIT_FACTOR)
        return "$digit1$digit2" == extractDigit(cpfDigits)
    }
}
