package com.example

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.testing.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlin.test.*

class ApiTest {

    @OptIn(ExperimentalSerializationApi::class)
    private fun ApplicationTestBuilder.createClient(): HttpClient {
        val client = createClient {
            install(ContentNegotiation) {
                json(
                    contentType = ContentType.Application.Json,
                    json = kotlinx.serialization.json.Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                        explicitNulls = false
                    }
                )
            }
        }
        return client
    }

    @Test
    fun testRoot() = testApplication {
        client.get("/").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals("Curso Clean Code e Clean Architect - Turma 18 - Branas.io", bodyAsText())
        }
    }

    @Test
    fun givenValidPassenger_whenCallSignUp_thenCreatePassenger() = testApplication {
        // given
        val client = createClient()
        val account = Account(
            name = "John Doe",
            cpf = "17463269051",
            email = "john.doe${Math.random()}@gmail.com",
            isPassenger = true,
            password = "123456"
        )
        // when
        val response: AccountResponse = client.post("/signup") {
            contentType(ContentType.Application.Json)
            setBody(account)
        }.apply {
            assertEquals(HttpStatusCode.Created, status)
        }.body()
        // then
        assertNotNull(response.accountId)
        val accountResponse = client.get("/accounts/${response.accountId}").apply {
            assertEquals(HttpStatusCode.OK, status)
        }.body<Account>()
        assertEquals(response.accountId, accountResponse.accountId)
        assertEquals(account.name, accountResponse.name)
        assertEquals(account.email, accountResponse.email)
        assertEquals(account.cpf, accountResponse.cpf)
        assertEquals(account.isPassenger, accountResponse.isPassenger)
        assertEquals(account.isDriver, accountResponse.isDriver)
        assertEquals(account.carPlate, accountResponse.carPlate)
    }

    @Test
    fun givenValidDriver_whenCallSignUp_thenCreateDriver() = testApplication {
        // given
        val client = createClient()
        val account = Account(
            name = "John Doe",
            cpf = "17463269051",
            email = "john.doe${Math.random()}@gmail.com",
            isDriver = true,
            carPlate = "ABC1234",
            password = "123456"
        )
        // when
        val response: AccountResponse = client.post("/signup") {
            contentType(ContentType.Application.Json)
            setBody(account)
        }.apply {
            assertEquals(HttpStatusCode.Created, status)
        }.body()
        // then
        assertNotNull(response.accountId)
        val accountResponse = client.get("/accounts/${response.accountId}").apply {
            assertEquals(HttpStatusCode.OK, status)
        }.body<Account>()
        assertEquals(response.accountId, accountResponse.accountId)
        assertEquals(account.name, accountResponse.name)
        assertEquals(account.email, accountResponse.email)
        assertEquals(account.cpf, accountResponse.cpf)
        assertEquals(account.isPassenger, accountResponse.isPassenger)
        assertEquals(account.isDriver, accountResponse.isDriver)
        assertEquals(account.carPlate, accountResponse.carPlate)
    }

    @Test
    fun givenAnInvalidName_whenCallSignup_thenReturnUnprocessableEntity() = testApplication {
        // given
        val client = createClient()
        val account = Account(
            name = "John",
            cpf = "17463269051",
            email = "john.doe${Math.random()}@gmail.com",
            isPassenger = true,
            password = "123456"
        )
        // when
        val response = client.post("/signup") {
            contentType(ContentType.Application.Json)
            setBody(account)
        }.apply {
            assertEquals(HttpStatusCode.UnprocessableEntity, status)
        }.bodyAsText()
        // then
        assertEquals(response, "Invalid name")
    }

    @Test
    fun givenAnInvalidEmail_whenCallSignup_thenReturnUnprocessableEntity() = testApplication {
        // given
        val client = createClient()
        val account = Account(
            name = "John Doe",
            cpf = "17463269051",
            email = "john.doe${Math.random()}",
            isPassenger = true,
            password = "123456"
        )
        // when
        val response = client.post("/signup") {
            contentType(ContentType.Application.Json)
            setBody(account)
        }.apply {
            assertEquals(HttpStatusCode.UnprocessableEntity, status)
        }.bodyAsText()
        // then
        assertEquals(response, "Invalid email")
    }

    @Test
    fun givenAnInvalidCpf_whenCallSignup_thenReturnUnprocessableEntity() = testApplication {
        // given
        val client = createClient()
        val account = Account(
            name = "John Doe",
            cpf = "1746326905",
            email = "john.doe${Math.random()}@gmail.com",
            isPassenger = true,
            password = "123456"
        )
        // when
        val response = client.post("/signup") {
            contentType(ContentType.Application.Json)
            setBody(account)
        }.apply {
            assertEquals(HttpStatusCode.UnprocessableEntity, status)
        }.bodyAsText()
        // then
        assertEquals(response, "Invalid cpf")
    }

    @Test
    fun givenDuplicateEmail_whenCallSignUp_thenReturnUnprocessableEntity() = testApplication {
        // given
        val client = createClient()
        val account = Account(
            name = "John Doe",
            cpf = "17463269051",
            email = "john.doe${Math.random()}@gmail.com",
            isPassenger = true,
            password = "123456"
        )
        // when
        client.post("/signup") {
            contentType(ContentType.Application.Json)
            setBody(account)
        }.apply {
            assertEquals(HttpStatusCode.Created, status)
        }
        val errorResponse = client.post("/signup") {
            contentType(ContentType.Application.Json)
            setBody(account)
        }.apply {
            assertEquals(HttpStatusCode.UnprocessableEntity, status)
        }.bodyAsText()
        // then
        assertEquals(errorResponse, "Duplicated account")
    }

    @Test
    fun givenAnInvalidCarPlate_whenCallSignup_thenReturnUnprocessableEntity() = testApplication {
        // given
        val client = createClient()
        val account = Account(
            name = "John Doe",
            cpf = "17463269051",
            email = "john.doe${Math.random()}@gmail.com",
            isDriver = true,
            carPlate = "1BC123",
            password = "123456"
        )
        // when
        val response = client.post("/signup") {
            contentType(ContentType.Application.Json)
            setBody(account)
        }.apply {
            assertEquals(HttpStatusCode.UnprocessableEntity, status)
        }.bodyAsText()
        // then
        assertEquals(response, "Invalid car plate")
    }

}
