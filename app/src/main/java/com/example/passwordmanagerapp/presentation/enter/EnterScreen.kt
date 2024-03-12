package com.example.passwordmanagerapp.presentation.enter

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EnterScreen(
    onClickListener: () -> Unit
) {
    Column {
        Text(text = "enter screen")
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = {
            onClickListener()
        }) {
            Text(text ="to main")
        }
    }
}