package com.example.passwordmanagerapp.domain.repositories

interface RepositoryEnter {
    fun checkMasterPassword(masterPassword: String): Boolean

    fun checkFingerPrint(): Boolean

    fun resetMasterPassword(masterPassword: String)

    fun checkIsMasterPasswordNull(): Boolean
}
