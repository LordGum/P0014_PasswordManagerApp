package com.example.passwordmanagerapp.domain.entities


data class Website(
    val id: Int = UNDEFINED_ID,
    val iconFileName: String = "image$id",
    var address: String,
    var name: String,
    var cipheredLogin: String,
    var cipheredPassword: String,
    var comment: String
) {
    companion object {
        const val UNDEFINED_ID = 0
    }
}