package com.example.passwordmanagerapp.data.repositories

import android.content.Context
import com.example.passwordmanagerapp.domain.repositories.RepositoryEnter
import com.example.passwordmanagerapp.security.CryptoManager
import javax.inject.Inject

class RepositoryEnterImpl @Inject constructor(
    private val context: Context,
    private val cryptoManager: CryptoManager
): RepositoryEnter {

    private val sharedPreferences = context.getSharedPreferences("PasswordManager", Context.MODE_PRIVATE)

    override suspend fun checkMasterPassword(masterPassword: String): Boolean {
        val cipheredMasterPassport = sharedPreferences.getString(CIPHERED_MASTER_PASSWORD, null)
        if (cipheredMasterPassport == null) throw RuntimeException("cipheredMasterPassport is null")
        else {
            val password =
                cryptoManager.decrypt(cipheredMasterPassport)
                    ?: throw RuntimeException("masterPassword is null")
            return (masterPassword == password)
        }
    }

    override suspend fun checkFingerPrint(): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun resetMasterPassword(masterPassword: String) {
        val cipheredMasterPassword = cryptoManager.encrypt(masterPassword)

        val editor = sharedPreferences.edit()
        editor.putString(CIPHERED_MASTER_PASSWORD, cipheredMasterPassword)
        editor.apply()
    }

    companion object {
        private const val CIPHERED_MASTER_PASSWORD = "cipheredMasterPassword"
    }
}