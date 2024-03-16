package com.example.passwordmanagerapp.presentation.enter

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.example.passwordmanagerapp.presentation.enter.ui_enter.UsualEnterScreen
import com.example.passwordmanagerapp.presentation.enter.ui_enter.WelcomePasswordScreen
import kotlinx.coroutines.launch

@Composable
fun EnterScreen(
    onClickListener: () -> Unit,
    viewModel: EnterViewModel
) {
    if(viewModel.checkIsNullMasterPassword()) {
        WelcomePasswordScreen { password ->
            viewModel.resetMasterPassword(password)
        }
    } else {
        UsualScreenWithSnackBar(
            onClickListener = onClickListener,
            viewModel = viewModel
        )
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
                isFingerPrintEnable = true,
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