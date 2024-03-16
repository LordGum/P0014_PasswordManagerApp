package com.example.passwordmanagerapp.security

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import android.util.Log
import java.nio.charset.StandardCharsets
import java.security.KeyStore
import java.util.Calendar
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.inject.Inject

class CryptoManager @Inject constructor() {


    private val keyStore = KeyStore.getInstance("AndroidKeyStore").apply {
        load(null)
    }

    private fun getKey(): SecretKey {
        Log.d("MATAG", "вызываем getKey()")
        val existingKey = keyStore.getEntry(ALIAS, null) as? KeyStore.SecretKeyEntry
        return existingKey?.secretKey ?: createKey()
    }

    private fun createKey(): SecretKey {
        Log.d("MATAG", "вызываем create()")

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
                    .setBlockModes(BLOCK_MODE)
                    .setEncryptionPaddings(PADDING)
                    .build()
            )
        }.generateKey()
    }


    fun encrypt(plainText: String): String? {

        return try {
            val cipher = Cipher.getInstance(TRANSFORMATION)
            cipher.init(Cipher.ENCRYPT_MODE, getKey())

            val cipherText = Base64.encodeToString(cipher.doFinal(plainText.toByteArray()), Base64.DEFAULT)
            val iv = Base64.encodeToString(cipher.iv, Base64.DEFAULT)

            "${cipherText}.$iv"
        } catch (e: Exception) {
            Log.e("MATAG", "encrypt: error msg = ${e.message}")
            null
        }
    }

    fun decrypt( cipherText: String): String? {

        val array = cipherText.split(".")
        val cipherData = Base64.decode(array[0], Base64.DEFAULT)
        val iv = Base64.decode(array[1], Base64.DEFAULT)

        return try {
            val cipher = Cipher.getInstance(TRANSFORMATION)
            val spec = IvParameterSpec(iv)

            cipher.init(Cipher.DECRYPT_MODE, getKey(), spec)

            val clearText = cipher.doFinal(cipherData)

            String(clearText, 0, clearText.size, StandardCharsets.UTF_8)
        } catch (e: Exception) {
            Log.e("MATAG", "decrypt: error msg = ${e.message}")
            null
        }
    }

    companion object {
        private const val ALIAS = "alias"
        private const val ALGORITHM = KeyProperties.KEY_ALGORITHM_AES
        private const val BLOCK_MODE = KeyProperties.BLOCK_MODE_CBC
        private const val PADDING = KeyProperties.ENCRYPTION_PADDING_PKCS7
        private const val TRANSFORMATION = "$ALGORITHM/$BLOCK_MODE/$PADDING"
    }
}