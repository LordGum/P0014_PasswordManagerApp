package com.example.passwordmanagerapp.domain.entities


data class Website(
    val id: Int = UNDEFINED_ID,
    val iconFileName: String = "image$id",
    val address: String,
    var name: String,
    val account: List<WebsiteAccount>
) {
    companion object {
        const val UNDEFINED_ID = 0
    }
}