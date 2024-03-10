package com.example.passwordmanagerapp.domain.entities


data class Website(
    val id: Int,
    val iconUrl: String,
    val address: String,
    var name: String,
    val listOfAccounts: List<WebsiteAccount>
)