package com.example.passwordmanagerapp.presentation.detail.entities_states

import android.os.Parcelable
import com.example.passwordmanagerapp.domain.entities.WebsiteAccount
import kotlinx.parcelize.Parcelize

@Parcelize
data class WebsiteState(
    var address: String,
    var name: String,
    var accountList: List<WebsiteAccount>
): Parcelable

