package com.example.passwordmanagerapp.domain.entities

//TODO доработать password
data class WebsiteAccount (
    val login: String,
    val password: String,
    var comment: String
)