package com.example.vpweek6

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.vpweek6.navigation.AppNavHost
import com.example.vpweek6.ui.theme.VPWEEK6Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VPWEEK6Theme {
                AppNavHost()
            }
        }
    }
}
