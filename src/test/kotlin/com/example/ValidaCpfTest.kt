package com.example

import kotlin.test.*

class ValidaCpfTest {
    @Test
    fun givenValidCpfWithoutSeparators_whenCallValidateCpf_thenShouldReturnTrue() {
        assertTrue(validateCpf("22068519038"))
    }

    @Test
    fun givenValidCpfWithSeparators_whenCallValidateCpf_thenShouldReturnTrue() {
        assertTrue(validateCpf("220.685.190-38"))
    }

    @Test
    fun givenInValidCpfWithLessThan11Numbers_whenCallValidateCpf_thenShouldReturnFalse() {
        assertFalse(validateCpf("2206851903"))
    }

    @Test
    fun givenInValidCpfWithoutSeparators_whenCallValidateCpf_thenShouldReturnFalse() {
        // 22068519048 first validate digit is wrong
        assertFalse(validateCpf("22068519048"))
        // 22068519031 second validate digit is wrong
        assertFalse(validateCpf("22068519031"))
        // 12068519038 first number is wrong
        assertFalse(validateCpf("12068519038"))
    }

    @Test
    fun givenInValidCpfWithSameNumbers_whenCallValidateCpf_thenShouldReturnFalse() {
        assertFalse(validateCpf("00000000000"))
        assertFalse(validateCpf("11111111111"))
    }

    @Test
    fun givenInValidCpfWithEmptyString_whenCallValidateCpf_thenShouldReturnFalse() {
        assertFalse(validateCpf(""))
    }

    @Test
    fun givenInValidCpfWithLetterInString_whenCallValidateCpf_thenShouldReturnFalse() {
        // A2068519038 first digit is a letter
        assertFalse(validateCpf("A2068519038"))
        // 2206851903a last digit is a letter
        assertFalse(validateCpf("2206851903a"))
    }
}