package com.ilhomsoliev.paging.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ilhomsoliev.paging.app.theme.PagingTheme
import com.ilhomsoliev.paging.presentation.ui.main.MainScreen
import com.ilhomsoliev.paging.presentation.viewmodel.MainViewModel
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PagingTheme {
                val vm = koinViewModel<MainViewModel>()
                MainScreen(vm)
            }
        }
    }
}