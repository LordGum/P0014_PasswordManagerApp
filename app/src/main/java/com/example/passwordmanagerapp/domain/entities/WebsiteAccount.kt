package com.example.passwordmanagerapp.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class WebsiteAccount (
    val id: Int = Website.UNDEFINED_ID,
    val cipherLogin: String,
    val cipherPassword: String,
    var comment: String = ""
):Parcelable {
    companion object {
        const val UNDEFINED_ID = 0
    }
}