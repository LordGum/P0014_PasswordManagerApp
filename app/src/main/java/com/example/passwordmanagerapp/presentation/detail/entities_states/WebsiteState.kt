package com.example.passwordmanagerapp.presentation.detail.entities_states

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import com.example.passwordmanagerapp.domain.entities.WebsiteAccount
import kotlinx.parcelize.Parcelize

@Parcelize
data class WebsiteState(
    val address: String,
    var name: String,
    var accountList: List<WebsiteAccount>
): Parcelable

@Composable
fun rememberWebsiteState(address: String, name: String, accountList: List<WebsiteAccount>): MutableState<WebsiteState> {
    return rememberSaveable {
        mutableStateOf(WebsiteState(address, name, accountList))
    }
}
