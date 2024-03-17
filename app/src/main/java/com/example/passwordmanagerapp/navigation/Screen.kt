package com.example.passwordmanagerapp.navigation

import android.net.Uri
import com.example.passwordmanagerapp.domain.entities.Website
import com.google.gson.Gson

sealed class Screen(
    val route: String
) {
    data object EnterScreen: Screen(ROUTE_ENTER)
    data object MainScreen: Screen(ROUTE_MAIN)
    data object DetailScreen: Screen(ROUTE_DETAIL) {
        private const val ROUTE_FOR_ARGS = "route_detail"

        fun getArgs(website: Website): String {
            val websiteJson = Gson().toJson(website)
            return "$ROUTE_FOR_ARGS/${websiteJson.encode()}"
        }
    }
    data object FingerPrintScreen: Screen(ROUTE_FINGER_PRINT)


    companion object {
        const val WEBSITE = "id"

        private const val ROUTE_ENTER = "route_enter"
        private const val ROUTE_MAIN = "route_main"
        private const val ROUTE_DETAIL = "route_detail/{$WEBSITE}"
        private const val ROUTE_FINGER_PRINT = "route_finger_print"
    }
}

fun String.encode(): String {
    return Uri.encode(this)
}