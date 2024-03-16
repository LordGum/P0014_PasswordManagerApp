package com.example.passwordmanagerapp.presentation.enter.ui_enter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.passwordmanagerapp.R


@Composable
fun WelcomePasswordScreen(
    onSaveClick: (String) -> Unit
) {
    val pass1 = rememberSaveable { mutableStateOf("") }
    val pass2 = rememberSaveable { mutableStateOf("") }
    val errorPassword1 = rememberSaveable { mutableStateOf(false) }
    val errorPassword2 = rememberSaveable { mutableStateOf(false) }
    val enableButton = rememberSaveable { mutableStateOf(false) }

    enableButton.value = (pass1.value == pass2.value  &&  pass1.value.isNotBlank())

    WelcomePasswordScreenContext(
        errorListener = { error, boolean ->
            when(error) {
                ErrorWelcome.ERROR_PASSWORD1 -> errorPassword1.value = boolean
                ErrorWelcome.ERROR_PASSWORD2 -> errorPassword2.value = boolean
            }
        },
        errorPassword1 = errorPassword1,
        errorPassword2 = errorPassword2,
        onPassword1Change = {
            pass1.value = it
        },
        onPassword2Change = {
            pass2.value = it
        },
        onSaveClick = {
            onSaveClick(pass1.value)
        },
        isEnableSaveButton = enableButton
    )
}

@Composable
private fun WelcomePasswordScreenContext(
    errorListener: (ErrorWelcome, Boolean) -> Unit,
    errorPassword1: State<Boolean>,
    errorPassword2: State<Boolean>,
    onPassword1Change: (String) -> Unit,
    onPassword2Change: (String) -> Unit,
    onSaveClick: () -> Unit,
    isEnableSaveButton: State<Boolean>
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            color = MaterialTheme.colorScheme.primary,
            text = stringResource(R.string.welcome),
            fontSize = 30.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(8.dp))

        PasswordTextField(
            errorStatePassword = errorPassword1,
            onTextChange = { password1 ->
                if (password1.trim().isBlank()) errorListener(ErrorWelcome.ERROR_PASSWORD1, true)
                else errorListener(ErrorWelcome.ERROR_PASSWORD1, false)
                onPassword1Change(password1)
            },
            label = stringResource(R.string.enter_master_password)
        )
        Spacer(modifier = Modifier.height(8.dp))

        PasswordTextField(
            errorStatePassword = errorPassword2,
            onTextChange = { password2 ->
                if (password2.trim().isBlank()) errorListener(ErrorWelcome.ERROR_PASSWORD2, true)
                else errorListener(ErrorWelcome.ERROR_PASSWORD2, false)
                onPassword2Change(password2)
            },
            label = stringResource(R.string.repeate_password)
        )
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            onClick = { onSaveClick() },
            enabled = isEnableSaveButton.value
        ) {
            Text(text = stringResource(R.string.save), color = Color.White)
        }
    }
}

@Composable
private fun PasswordTextField(
    errorStatePassword: State<Boolean>,
    onTextChange: (String) -> Unit,
    label: String
) {
    var password by rememberSaveable { mutableStateOf("") }
    var passwordHidden by rememberSaveable { mutableStateOf(true) }

    OutlinedTextField(
        value = password,
        onValueChange = {  newText ->
            password = newText
            onTextChange(newText)
        },
        singleLine = true,
        label = { Text(label) },
        visualTransformation =
        if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            IconButton(onClick = { passwordHidden = !passwordHidden }) {
                val visibilityIcon =
                    if (passwordHidden) getVisibility() else getVisibilityOff()
                val description = if (passwordHidden) stringResource(R.string.show_password) else stringResource(
                    R.string.hide_password
                )
                Icon(imageVector = visibilityIcon, contentDescription = description)
            }
        },
        modifier = Modifier.fillMaxWidth(),
        isError = errorStatePassword.value
    )
}


enum class ErrorWelcome{
    ERROR_PASSWORD1, ERROR_PASSWORD2
}