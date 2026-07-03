package com.example.appmudanza

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.appmudanza.navigation.AppNavGraph
import com.example.appmudanza.ui.theme.AppMudanzaTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppMudanzaTheme {
                AppNavGraph()
            }
        }
    }
}