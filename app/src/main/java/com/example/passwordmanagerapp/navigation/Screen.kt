package com.example.passwordmanagerapp.navigation

sealed class Screen(
    val route: String
) {
    object EnterScreen: Screen(ROUTE_ENTER)
    object MainScreen: Screen(ROUTE_MAIN)
    object DetailScreen: Screen(ROUTE_DETAIL) {
        private const val ROUTE_FOR_ARGS = "route_detail"

        fun getArgs(id: Int): String {
            return "$ROUTE_FOR_ARGS/$id"
        }
    }

    companion object {
        const val WEBSITE_ID = "id"

        const val ROUTE_ENTER = "route_enter"
        const val ROUTE_MAIN = "route_main"
        const val ROUTE_DETAIL = "route_detail/{$WEBSITE_ID}"
    }
}
