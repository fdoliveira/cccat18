package com.example

fun signup(input: Account): Response {
    getAccountByEmail(input.email)?.let { throw Exception("Duplicated account") }
    (!input.name.matches(Regex("^[a-zA-Z]+ [a-zA-Z]+$"))) && throw Exception("Invalid name")
    (!input.email.matches(Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"))) && throw Exception("Invalid email")
    (!validateCpf(input.cpf)) && throw Exception("Invalid cpf")
    (input.isDriver && (input.carPlate == null || (!input.carPlate.matches(Regex("^[A-Z]{3}[0-9]{4}$"))))) && throw Exception("Invalid car plate")
    return Response(saveAccount(input))
}

fun getAccount(accountId: String): Account {
    return getAccountById(accountId) ?: throw Exception("Account not found")
}