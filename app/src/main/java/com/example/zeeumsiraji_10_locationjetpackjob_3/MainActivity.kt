package com.example.zeeumsiraji_10_locationjetpackjob_3

import MainScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent



import com.example.zeeumsiraji_10_locationjetpackjob_3.ui.theme.TravelAppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TravelAppTheme {
                MainScreen()
            }
        }
    }
}
