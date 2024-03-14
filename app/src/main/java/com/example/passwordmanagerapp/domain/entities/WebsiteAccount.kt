package com.example.passwordmanagerapp.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class WebsiteAccount (
    val id: Int = UNDEFINED_ID,
    var cipherLogin: String,
    var cipherPassword: String,
    var comment: String = ""
):Parcelable {
    companion object {
        const val UNDEFINED_ID = 0
    }
}