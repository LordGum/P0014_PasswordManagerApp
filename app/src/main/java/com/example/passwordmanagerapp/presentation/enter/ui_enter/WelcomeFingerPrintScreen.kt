package com.example.passwordmanagerapp.presentation.enter.ui_enter

import android.widget.Toast
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.example.passwordmanagerapp.R
import com.example.passwordmanagerapp.security.CryptoManagerFinger

@Composable
fun WelcomeFingerPrintScreen(
    onNoClick: () -> Unit,
    onSuccess: () -> Unit
) {
    val yesClickState = remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            color = MaterialTheme.colorScheme.primary,
            text = stringResource(R.string.add_enter_by_finger_print),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(18.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = {
                yesClickState.value = true
            }) {
                Text(text = stringResource(R.string.yes), color = Color.White)
            }
            Button(onClick = {
                onNoClick()
            }) {
                Text(text = stringResource(R.string.no), color = Color.White)
            }
        }
    }

    if (yesClickState.value) {
        Dialog (
            onSuccess = onSuccess,
            onNoClick = onNoClick
        )
    }
}

@Composable
fun Dialog(
    onNoClick: () -> Unit,
    onSuccess: () -> Unit
) {
    val applicationContext = LocalContext.current.applicationContext
    val context = LocalContext.current
    val executor = ContextCompat.getMainExecutor(context)
    val biometricPrompt = BiometricPrompt(LocalContext.current as FragmentActivity, executor,
        object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(
                errorCode: Int,
                errString: CharSequence,
            ) {
                super.onAuthenticationError(errorCode, errString)
                onNoClick()
            }

            override fun onAuthenticationSucceeded(
                result: BiometricPrompt.AuthenticationResult,
            ) {
                super.onAuthenticationSucceeded(result)

                onSuccess()
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Toast.makeText(applicationContext, "Authentication failed",
                    Toast.LENGTH_SHORT)
                    .show()
            }
        })

    val promptInfo = BiometricPrompt.PromptInfo.Builder()
        .setTitle(stringResource(R.string.enter_string1))
        .setSubtitle(stringResource(R.string.enter_string2))
        .setNegativeButtonText(stringResource(R.string.cancel))
        .build()

    biometricPrompt.authenticate(promptInfo)
    val cryptoManagerFinger = CryptoManagerFinger()
    cryptoManagerFinger.encryptFinger(biometricPrompt, promptInfo)
}
