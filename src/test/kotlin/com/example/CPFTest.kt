package com.example

import com.example.domain.CPF
import kotlin.test.*

class CPFTest {
    @Test
    fun givenValidCpfWithoutSeparators_whenCallValidateCpf_thenShouldReturnTrue() {
        CPF("22068519038")
    }

    @Test
    fun givenValidCpfWithSeparators_whenCallValidateCpf_thenShouldReturnTrue() {
        CPF("220.685.190-38")
    }

    @Test
    fun givenInValidCpfWithLessThan11Numbers_whenCallValidateCpf_thenShouldReturnFalse() {
        assertFailsWith<Exception> {
            CPF("2206851903")}
    }

    @Test
    fun givenInValidCpfWithoutSeparators_whenCallValidateCpf_thenShouldReturnFalse() {
        // 22068519048 first validate digit is wrong
        assertFailsWith<Exception> {
            CPF("22068519048")}
        // 22068519031 second validate digit is wrong
        assertFailsWith<Exception> {
            CPF("22068519031")}
        // 12068519038 first number is wrong
        assertFailsWith<Exception> {
            CPF("12068519038")}
    }

    @Test
    fun givenInValidCpfWithSameNumbers_whenCallValidateCpf_thenShouldReturnFalse() {
        assertFailsWith<Exception> {
            CPF("00000000000")}
        assertFailsWith<Exception> {
            CPF("11111111111")}
    }

    @Test
    fun givenInValidCpfWithEmptyString_whenCallValidateCpf_thenShouldReturnFalse() {
        assertFailsWith<Exception> {
            CPF("")}
    }

    @Test
    fun givenInValidCpfWithLetterInString_whenCallValidateCpf_thenShouldReturnFalse() {
        // A2068519038 first digit is a letter
        assertFailsWith<Exception> {
            CPF("A2068519038")}
        // 2206851903a last digit is a letter
        assertFailsWith<Exception> {
            CPF("2206851903a")}
    }
}