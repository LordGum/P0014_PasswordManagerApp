package com.example.passwordmanagerapp.domain.entities


data class WebsiteAccount (
    val cipherLogin: String,
    val cipherPassword: String,
    var comment: String = ""
)