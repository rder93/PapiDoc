package com.papidoc.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.papidoc.core.navigation.PapiDocNavGraph
import com.papidoc.core.ui.theme.PapiDocTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PapiDocTheme {
                val navController = rememberNavController()
                PapiDocNavGraph(navController = navController)
            }
        }
    }
}
