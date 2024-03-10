package com.example.passwordmanagerapp.domain.entities

//TODO доработать password
data class WebsiteAccount (
    val cipherLogin: String,
    val cipherPassword: String,
    var comment: String
)