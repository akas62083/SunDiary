package com.akas62083.sundiary

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.akas62083.sundiary.screenofdetail.DetailScreen
import com.akas62083.sundiary.screenofdetail.DetailViewModel
import com.akas62083.sundiary.screenofhome.HomeScreen
import com.akas62083.sundiary.screenofhome.HomeViewModel
import com.akas62083.sundiary.screenofstar.StarScreen
import com.akas62083.sundiary.screenofstar.StarViewModel
import com.akas62083.sundiary.screenofwritediary.WriteScreen
import com.akas62083.sundiary.screenofwritediary.WriteViewModel
import com.akas62083.sundiary.ui.theme.SunDiaryTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SunDiaryTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background,
                ) {
                    Greeting()
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Greeting() {
    val navController = rememberNavController()
    val currentBackStack by navController.currentBackStackEntryAsState()
    NavHost(
        navController = navController,
        startDestination = Route.HomeScreen,
        modifier = Modifier.fillMaxSize()
    ) {
        composable<Route.HomeScreen>() { backStackEntry ->
            val viewModel: HomeViewModel = hiltViewModel()
            HomeScreen(
                viewModel = viewModel,
                navController = navController,
            )
        }
        composable<Route.WriteScreen>(
            enterTransition = {
                slideInHorizontally(
                    animationSpec = tween(500),
                    initialOffsetX = { it }
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    animationSpec = tween(400),
                    targetOffsetX = { it }
                )
            }
        ) { backStackEntry ->
            val viewModel: WriteViewModel = hiltViewModel()
            WriteScreen(
                viewModel = viewModel,
                navController = navController,
            )
        }
        composable<Route.DetailScreen>() { backStackEntry ->
            val viewModel: DetailViewModel = hiltViewModel()
            DetailScreen(
                viewModel = viewModel,
                navController = navController
            )
        }
        composable<Route.StarScreen>() { backStackEntry ->
            val viewModel: StarViewModel = hiltViewModel()
            StarScreen(
                viewModel = viewModel,
                navController = navController
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SunDiaryTheme {
        Greeting()
    }
}