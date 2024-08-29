package com.gajanan.moivezapp.navigation

sealed class Screen(val route : String) {
    data object HomeScreen : Screen("destination_home")
    data object DetailScreen : Screen("destination_developer")
    fun withArgs(vararg args: String) : String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}