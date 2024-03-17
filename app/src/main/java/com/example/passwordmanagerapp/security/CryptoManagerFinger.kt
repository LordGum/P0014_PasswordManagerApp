package com.example.passwordmanagerapp.security

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.biometric.BiometricPrompt
import androidx.biometric.BiometricPrompt.PromptInfo
import java.security.KeyStore
import java.util.Calendar
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.inject.Inject

class CryptoManagerFinger @Inject constructor() {

    private val keyStore = KeyStore.getInstance("AndroidKeyStore").apply {
        load(null)
    }

    private fun getCipher(): Cipher {
        return Cipher.getInstance(TRANSFORMATION)
    }

    private fun getKey(): SecretKey {
        val existingKey = keyStore.getEntry(ALIAS, null) as? KeyStore.SecretKeyEntry
        return existingKey?.secretKey ?: createKey()
    }

    private fun createKey(): SecretKey {
        val start: Calendar = Calendar.getInstance()
        val end: Calendar = Calendar.getInstance()
        end.add(Calendar.YEAR, 2)

        return KeyGenerator.getInstance(ALGORITHM).apply {
            init(
                KeyGenParameterSpec.Builder(
                    ALIAS,
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                )
                    .setKeyValidityStart(start.time)
                    .setKeyValidityEnd(end.time)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .setUserAuthenticationRequired(true)
                    .setInvalidatedByBiometricEnrollment(true)
                    .build()
            )
        }.generateKey()
    }

    fun encryptFinger(biometricPrompt: BiometricPrompt, promptInfo: PromptInfo) {
        val cipher = getCipher()
        val secretKey = getKey()
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        biometricPrompt.authenticate(promptInfo,
            BiometricPrompt.CryptoObject(cipher))
    }





    companion object {
        private const val ALIAS = "alias_for_print"

        private const val ALGORITHM = KeyProperties.KEY_ALGORITHM_AES
        private const val BLOCK_MODE = KeyProperties.BLOCK_MODE_CBC
        private const val PADDING = KeyProperties.ENCRYPTION_PADDING_PKCS7
        private const val TRANSFORMATION = "$ALGORITHM/$BLOCK_MODE/$PADDING"
    }
}