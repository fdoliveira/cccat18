package com.example

const val CPF_VALID_LENGTH = 11
const val FIRST_DIGIT_FACTOR = 10
const val SECOND_DIGIT_FACTOR = 11

fun allDigitsAreEqual(cpf: String): Boolean {
    return cpf.all { it == cpf[0] }
}

fun calculateDigit(cpf: String, pFactor: Int): Int {
    var total = 0
    var factor = pFactor
    for (digit in cpf) {
        if (factor > 1) total += digit.toString().toInt() * factor--
    }
    val remainder = total % 11
    return if (remainder < 2) 0 else 11 - remainder
}

fun extractDigit(cpf: String): String {
    return cpf.slice(9 until cpf.length)
}

fun validateCpf(cpf: String): Boolean {
    val cpfDigits = cpf.filter { it.isDigit() }
    if (cpfDigits.length != CPF_VALID_LENGTH) return false
    if (allDigitsAreEqual(cpfDigits)) return false
    val digit1 = calculateDigit(cpfDigits, FIRST_DIGIT_FACTOR)
    val digit2 = calculateDigit(cpfDigits, SECOND_DIGIT_FACTOR)
    return "$digit1$digit2" == extractDigit(cpf)
}