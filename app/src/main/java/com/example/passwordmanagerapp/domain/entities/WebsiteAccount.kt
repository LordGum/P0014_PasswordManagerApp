package com.example.passwordmanagerapp.domain.entities


data class WebsiteAccount (
    val id: Int = Website.UNDEFINED_ID,
    val cipherLogin: String,
    val cipherPassword: String,
    var comment: String = ""
)  {
    companion object {
        const val UNDEFINED_ID = 0
    }
}