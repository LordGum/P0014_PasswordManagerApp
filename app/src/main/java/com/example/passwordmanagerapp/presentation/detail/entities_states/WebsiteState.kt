package com.example.passwordmanagerapp.presentation.detail.entities_states

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WebsiteState(
    val id: Int = -1,
    var address: String,
    var name: String,
    var cipheredLogin: String,
    var cipheredPassword: String,
    var comment: String
): Parcelable