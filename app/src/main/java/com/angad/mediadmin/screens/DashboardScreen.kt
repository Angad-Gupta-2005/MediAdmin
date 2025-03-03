package com.angad.mediadmin.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DashboardScreen() {
    Scaffold { innerPadding ->
        Text(
            text = "Dashboard Screen",
            modifier = Modifier.padding(innerPadding)
        )
    }
}