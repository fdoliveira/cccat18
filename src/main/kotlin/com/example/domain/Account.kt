package com.example.domain

class Account {
    private val accountId: UUID?
    private val name: Name
    private val email: Email
    private val cpf: CPF
    private val isPassenger: Boolean
    private val isDriver: Boolean
    private val carPlate: CarPlate?
    private val password: String

    constructor(accountId: String? = null,
                name: String,
                email: String,
                cpf: String,
                isPassenger: Boolean = false,
                isDriver: Boolean = false,
                carPlate: String? = null,
                password: String) {
        this.accountId = accountId?.let { UUID(it) }
        this.name = Name(name)
        this.email = Email(email)
        this.cpf = CPF(cpf)
        this.isPassenger = isPassenger
        this.isDriver = isDriver
        this.carPlate = carPlate?.let { CarPlate(it) }
        this.password = password
    }

    companion object {
        fun create(name: String,
                   email: String,
                   cpf: String,
                   isPassenger: Boolean = false,
                   isDriver: Boolean = false,
                   carPlate: String? = null,
                   password: String) = Account(
            accountId = UUID.create().value,
            name = name,
            email = email,
            cpf = cpf,
            isPassenger = isPassenger,
            isDriver = isDriver,
            carPlate = carPlate,
            password = password
        )
    }

    fun from(accountId: String? = this.accountId?.value,
             name: String = this.name.value,
             email: String = this.email.value,
             cpf: String = this.cpf.value,
             isPassenger: Boolean = this.isPassenger,
             isDriver: Boolean = this.isPassenger,
             carPlate: String? = this.carPlate?.value,
             password: String = this.password) = Account(
        accountId = accountId,
        name = name,
        email = email,
        cpf = cpf,
        isPassenger = isPassenger,
        isDriver = isDriver,
        carPlate = carPlate,
        password = password
    )

    fun getAccountId(): String? = accountId?.value
    fun getName(): String = name.value
    fun getEmail(): String = email.value
    fun getCPF(): String = cpf.value
    fun isPassenger(): Boolean = isPassenger
    fun isDriver(): Boolean = isDriver
    fun getCarPlate(): String? = carPlate?.value
    fun getPassword(): String = password
}