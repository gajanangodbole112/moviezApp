package com.gajanan.moivezapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.gajanan.moivezapp.navigation.Screen
import com.gajanan.moivezapp.ui.screens.HomeScreen
import com.gajanan.moivezapp.ui.screens.MovieDetailScreen
import com.gajanan.moivezapp.ui.theme.MoiveZAppTheme
import com.gajanan.moivezapp.ui.viewModels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MoiveZAppTheme {
                NavigationControllerWrapper()
            }
        }
    }
}

@Composable
fun NavigationControllerWrapper(){
    val navController = rememberNavController()
    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navController,
        startDestination = Screen.HomeScreen.route
        )
    {
        composable(Screen.HomeScreen.route){
            HomeScreen { movieId ->
                navController.navigate(Screen.DetailScreen.withArgs(movieId.toString()))
            }
        }
        composable(
            route = Screen.DetailScreen.route + "/{movieId}",
            arguments = listOf(
                navArgument("movieId"){
                    type = NavType.StringType
                }
            )
        ){ entry ->
            MovieDetailScreen( movieId = entry.arguments?.getString("movieId"))
        }
    }
}