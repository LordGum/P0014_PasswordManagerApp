package com.example.passwordmanagerapp.presentation.enter

import android.util.Log
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_WEAK
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.example.passwordmanagerapp.R
import com.example.passwordmanagerapp.presentation.enter.ui_enter.UsualEnterScreen
import com.example.passwordmanagerapp.presentation.enter.ui_enter.WelcomePasswordScreen
import kotlinx.coroutines.launch

@Composable
fun EnterScreen(
    onAddFingerPrint: () -> Unit,
    onClickListener: () -> Unit,
    viewModel: EnterViewModel
) {
    val isAvailableFingerAuth = remember { mutableStateOf(false) }
    if(viewModel.checkIsNullMasterPassword()) {
        Log.d("MATAG", "finger check")
        val biometricManager = BiometricManager.from(LocalContext.current.applicationContext)
        when (biometricManager.canAuthenticate(BIOMETRIC_WEAK)) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                Log.d("MATAG", "enable")
                isAvailableFingerAuth.value = true
                Log.d("MY_APP_TAG", "App can authenticate using biometrics.")
            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE ->
                Log.e("MY_APP_TAG", "No biometric features available on this device.")
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE ->
                Log.e("MY_APP_TAG", "Biometric features are currently unavailable.")
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                Log.d("MY_APP_TAG", "you can enroll")
                isAvailableFingerAuth.value = true
            }
            BiometricManager.BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED ->
                Log.d("MY_APP_TAG", "BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED")
            BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED -> {
                Log.d("MY_APP_TAG", "BIOMETRIC_ERROR_UNSUPPORTED")
            }
            BiometricManager.BIOMETRIC_STATUS_UNKNOWN ->
                Log.d("MY_APP_TAG", "BIOMETRIC_STATUS_UNKNOWN")
        }
        WelcomePasswordScreen { password ->
            viewModel.resetMasterPassword(password)
            if (isAvailableFingerAuth.value) onAddFingerPrint()
            else onClickListener()
        }
    } else {
        UsualScreenWithSnackBar(
            onClickListener = onClickListener,
            viewModel = viewModel
        )

        val context = LocalContext.current
        val executor = ContextCompat.getMainExecutor(context)
        val biometricPrompt = BiometricPrompt(LocalContext.current as FragmentActivity, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult,
                ) {
                    super.onAuthenticationSucceeded(result)
                    onClickListener()
                }
            })
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(stringResource(R.string.enter_string1))
            .setSubtitle(stringResource(R.string.enter_string2))
            .setNegativeButtonText(stringResource(R.string.enter_with_password))
            .build()

        biometricPrompt.authenticate(promptInfo)
    }

}

@Composable
fun UsualScreenWithSnackBar(
    onClickListener: () -> Unit,
    viewModel: EnterViewModel
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content = { innerPadding ->
            UsualEnterScreen(
                modifier = Modifier.padding(innerPadding),
                checkPassword = { password ->
                    if(viewModel.checkMasterPassword(password)) {
                        onClickListener()
                    } else {
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                "Введен неверный пароль"
                            )
                        }
                    }
                }
            )
        }
    )
}