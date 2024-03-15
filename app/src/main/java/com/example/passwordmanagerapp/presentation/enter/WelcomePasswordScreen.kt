package com.example.passwordmanagerapp.presentation.enter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.passwordmanagerapp.R


@Composable
fun WelcomePasswordScreen() {
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
            //TODO
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
                    if (passwordHidden) visibility else visibilityOff
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

private val visibility: ImageVector
    get() {
        if (_visibility != null) {
            return _visibility!!
        }
        _visibility = materialIcon(name = "Filled.Visibility") {
            materialPath {
                moveTo(12.0f, 4.5f)
                curveTo(7.0f, 4.5f, 2.73f, 7.61f, 1.0f, 12.0f)
                curveToRelative(1.73f, 4.39f, 6.0f, 7.5f, 11.0f, 7.5f)
                reflectiveCurveToRelative(9.27f, -3.11f, 11.0f, -7.5f)
                curveToRelative(-1.73f, -4.39f, -6.0f, -7.5f, -11.0f, -7.5f)
                close()
                moveTo(12.0f, 17.0f)
                curveToRelative(-2.76f, 0.0f, -5.0f, -2.24f, -5.0f, -5.0f)
                reflectiveCurveToRelative(2.24f, -5.0f, 5.0f, -5.0f)
                reflectiveCurveToRelative(5.0f, 2.24f, 5.0f, 5.0f)
                reflectiveCurveToRelative(-2.24f, 5.0f, -5.0f, 5.0f)
                close()
                moveTo(12.0f, 9.0f)
                curveToRelative(-1.66f, 0.0f, -3.0f, 1.34f, -3.0f, 3.0f)
                reflectiveCurveToRelative(1.34f, 3.0f, 3.0f, 3.0f)
                reflectiveCurveToRelative(3.0f, -1.34f, 3.0f, -3.0f)
                reflectiveCurveToRelative(-1.34f, -3.0f, -3.0f, -3.0f)
                close()
            }
        }
        return _visibility!!
    }
private var _visibility: ImageVector? = null

private val visibilityOff: ImageVector
    get() {
        if (_visibilityOff != null) {
            return _visibilityOff!!
        }
        _visibilityOff = materialIcon(name = "Filled.VisibilityOff") {
            materialPath {
                moveTo(12.0f, 7.0f)
                curveToRelative(2.76f, 0.0f, 5.0f, 2.24f, 5.0f, 5.0f)
                curveToRelative(0.0f, 0.65f, -0.13f, 1.26f, -0.36f, 1.83f)
                lineToRelative(2.92f, 2.92f)
                curveToRelative(1.51f, -1.26f, 2.7f, -2.89f, 3.43f, -4.75f)
                curveToRelative(-1.73f, -4.39f, -6.0f, -7.5f, -11.0f, -7.5f)
                curveToRelative(-1.4f, 0.0f, -2.74f, 0.25f, -3.98f, 0.7f)
                lineToRelative(2.16f, 2.16f)
                curveTo(10.74f, 7.13f, 11.35f, 7.0f, 12.0f, 7.0f)
                close()
                moveTo(2.0f, 4.27f)
                lineToRelative(2.28f, 2.28f)
                lineToRelative(0.46f, 0.46f)
                curveTo(3.08f, 8.3f, 1.78f, 10.02f, 1.0f, 12.0f)
                curveToRelative(1.73f, 4.39f, 6.0f, 7.5f, 11.0f, 7.5f)
                curveToRelative(1.55f, 0.0f, 3.03f, -0.3f, 4.38f, -0.84f)
                lineToRelative(0.42f, 0.42f)
                lineTo(19.73f, 22.0f)
                lineTo(21.0f, 20.73f)
                lineTo(3.27f, 3.0f)
                lineTo(2.0f, 4.27f)
                close()
                moveTo(7.53f, 9.8f)
                lineToRelative(1.55f, 1.55f)
                curveToRelative(-0.05f, 0.21f, -0.08f, 0.43f, -0.08f, 0.65f)
                curveToRelative(0.0f, 1.66f, 1.34f, 3.0f, 3.0f, 3.0f)
                curveToRelative(0.22f, 0.0f, 0.44f, -0.03f, 0.65f, -0.08f)
                lineToRelative(1.55f, 1.55f)
                curveToRelative(-0.67f, 0.33f, -1.41f, 0.53f, -2.2f, 0.53f)
                curveToRelative(-2.76f, 0.0f, -5.0f, -2.24f, -5.0f, -5.0f)
                curveToRelative(0.0f, -0.79f, 0.2f, -1.53f, 0.53f, -2.2f)
                close()
                moveTo(11.84f, 9.02f)
                lineToRelative(3.15f, 3.15f)
                lineToRelative(0.02f, -0.16f)
                curveToRelative(0.0f, -1.66f, -1.34f, -3.0f, -3.0f, -3.0f)
                lineToRelative(-0.17f, 0.01f)
                close()
            }
        }
        return _visibilityOff!!
    }
private var _visibilityOff: ImageVector? = null


enum class ErrorWelcome{
    ERROR_PASSWORD1, ERROR_PASSWORD2
}