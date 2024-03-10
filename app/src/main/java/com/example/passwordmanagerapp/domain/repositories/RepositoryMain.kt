package com.example.passwordmanagerapp.domain.repositories

interface RepositoryMain {
    suspend fun checkMasterPassword(masterPassword: String): Boolean

    suspend fun checkFingerPrint(): Boolean

    suspend fun resetMasterPassword(masterPassword: String)
}
