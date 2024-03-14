package com.example.passwordmanagerapp.domain.entities


data class Website(
    val id: Int = UNDEFINED_ID,
    val iconFileName: String = "image$id",
    var address: String,
    var name: String,
    val accountList: ArrayList<WebsiteAccount>
) {
    companion object {
        const val UNDEFINED_ID = -1
    }
}