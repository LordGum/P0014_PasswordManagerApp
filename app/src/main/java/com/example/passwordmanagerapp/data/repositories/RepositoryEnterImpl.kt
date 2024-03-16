package com.example.passwordmanagerapp.data.repositories

import android.app.Application
import android.content.Context
import android.util.Log
import com.example.passwordmanagerapp.domain.repositories.RepositoryEnter
import com.example.passwordmanagerapp.security.CryptoManager
import javax.inject.Inject

class RepositoryEnterImpl @Inject constructor(
    private val application: Application,
    private val cryptoManager: CryptoManager
): RepositoryEnter {

    private val sharedPreferences = application.getSharedPreferences("PasswordManager", Context.MODE_PRIVATE)

    override fun checkMasterPassword(masterPassword: String): Boolean {
        val cipheredMasterPassport = sharedPreferences.getString(CIPHERED_MASTER_PASSWORD, null)
        cipheredMasterPassport?.let {
            val password =
                cryptoManager.decrypt(it) ?: throw RuntimeException("masterPassword is null")
            return (masterPassword == password)
        }
        return false
    }

    override fun checkFingerPrint(): Boolean {
        TODO("Not yet implemented")
    }

    override fun resetMasterPassword(masterPassword: String) {
        val cipheredMasterPassword = cryptoManager.encrypt(masterPassword)
        val editor = sharedPreferences.edit()
        editor.putString(CIPHERED_MASTER_PASSWORD, cipheredMasterPassword)
        editor.apply()
    }

    override fun checkIsMasterPasswordNull(): Boolean {
        val cipheredMasterPassport = sharedPreferences.getString(CIPHERED_MASTER_PASSWORD, null)
        return (cipheredMasterPassport.isNullOrEmpty())
    }

    companion object {
        private const val CIPHERED_MASTER_PASSWORD = "cipheredMasterPassword"
    }
}