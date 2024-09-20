package com.example.infra.di

import com.example.infra.database.DatabaseConnection
import com.example.infra.database.DatabasePgsqlAdapter
import com.example.infra.repository.AccountRepository
import com.example.infra.repository.AccountRepositoryDatabase
import com.example.infra.repository.RideRepository
import com.example.infra.repository.RideRepositoryDatabase
import org.koin.dsl.module

val repositoryModule = module {
    single<DatabaseConnection> {
        DatabasePgsqlAdapter()
    }
    single<AccountRepository> {
        AccountRepositoryDatabase()
    }
    single<RideRepository> {
        RideRepositoryDatabase()
    }
}