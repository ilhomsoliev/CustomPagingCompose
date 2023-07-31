package com.ilhomsoliev.paging.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.fragula2.compose.FragulaNavHost
import com.fragula2.compose.rememberFragulaNavController
import com.fragula2.compose.swipeable
import com.ilhomsoliev.paging.app.theme.PagingTheme
import com.ilhomsoliev.paging.presentation.ui.description.DescriptionScreen
import com.ilhomsoliev.paging.presentation.ui.main.MainScreen
import com.ilhomsoliev.paging.presentation.viewmodel.DescriptionViewModel
import com.ilhomsoliev.paging.presentation.viewmodel.MainViewModel
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PagingTheme {
                Navigation()
            }
        }
    }
}

@Composable
fun Navigation() {
    val navController = rememberFragulaNavController()
    FragulaNavHost(navController, startDestination = "list") {
        swipeable("list") {
            val vm = koinViewModel<MainViewModel>()
            MainScreen(vm, navController)
        }
        swipeable(
            "details/{id}",
            listOf(navArgument("id") { type = NavType.IntType; defaultValue = -1 })
        ) {
            val vm = koinViewModel<DescriptionViewModel>()
            it.arguments?.getInt("id")?.let { id ->

                DescriptionScreen(vm = vm, navController = navController, characterId = id)
            }
        }
    }
}