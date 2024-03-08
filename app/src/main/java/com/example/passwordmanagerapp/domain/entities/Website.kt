package com.example.passwordmanagerapp.domain.entities

//TODO доработать address
data class Website(
    val id: Int,
    val iconUrl: String,
    val address: String,
    var name: String,
    val account: WebsiteAccount
)