package com.example.passwordmanagerapp.presentation.enter

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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.passwordmanagerapp.R


@Composable
fun UsualEnterScreen() {
    val password = rememberSaveable { mutableStateOf("") }
    val errorPassword = rememberSaveable { mutableStateOf(false) }
    val enableButton = rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.Center
    ) {
        PasswordTextField(
            errorStatePassword = errorPassword,
            onTextChange = { passwordGet ->
                errorPassword.value = passwordGet.trim().isBlank()
                enableButton.value = passwordGet.trim().isNotBlank()
                password.value = passwordGet
            },
            label = stringResource(id = R.string.enter_master_password)
        )
        Spacer(modifier = Modifier.height(18.dp))
        Button(
            onClick = { /*TODO*/ },
            enabled = enableButton.value,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.check), color = Color.White)
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
