package com.example.passwordmanagerapp.data

import com.example.passwordmanagerapp.domain.repositories.RepositoryMain
import com.example.passwordmanagerapp.security.CryptoManager
import javax.inject.Inject

class RepositoryMainImpl @Inject constructor(
    private val cryptoManager: CryptoManager
): RepositoryMain {
    override suspend fun checkMasterPassword(masterPassword: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun checkFingerPrint(): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun resetMasterPassword(masterPassword: String) {
        val cipherText = cryptoManager.encrypt(masterPassword)
        //todo сохранить в room
    }
}