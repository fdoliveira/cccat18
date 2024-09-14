package com.example

class Signup(val accountDAO: AccountDAO) {
    fun execute(input: Account): Response {
        accountDAO.getAccountByEmail(input.email)?.let { throw Exception("Duplicated account") }
        (!input.name.matches(Regex("^[a-zA-Z]+ [a-zA-Z]+$"))) && throw Exception("Invalid name")
        (!input.email.matches(Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"))) && throw Exception("Invalid email")
        (!validateCpf(input.cpf)) && throw Exception("Invalid cpf")
        (input.isDriver && (input.carPlate == null || (!input.carPlate.matches(Regex("^[A-Z]{3}[0-9]{4}$"))))) && throw Exception("Invalid car plate")
        return Response(accountDAO.saveAccount(input))
    }
}