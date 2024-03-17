package com.example.passwordmanagerapp.presentation.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.passwordmanagerapp.R
import com.example.passwordmanagerapp.domain.entities.Website
import com.example.passwordmanagerapp.presentation.detail.entities_states.WebsiteState
import com.example.passwordmanagerapp.security.CryptoManager

@Composable
fun DetailScreen(
    viewModel: DetailViewModel,
    website: Website,
    onBackIconClick: () -> Unit
) {
    val cryptoManager = CryptoManager()

    if (website.id != Website.UNDEFINED_ID) {
        val screenState = DetailScreenState.RefactorState
        val websiteState =  mutableStateOf(WebsiteState(
            id = website.id,
            name = website.name,
            address = website.address,
            cipheredLogin = cryptoManager.decrypt(website.cipheredLogin) ?: throw RuntimeException("login is null"),
            cipheredPassword = cryptoManager.decrypt(website.cipheredPassword)  ?: throw RuntimeException("password is null"),
            comment = website.comment
        ))
        val errorStateName = rememberSaveable { mutableStateOf(false) }
        val errorStateAddress = rememberSaveable { mutableStateOf(false) }
        val errorStateLogin = rememberSaveable { mutableStateOf(false) }
        val errorStatePassword = rememberSaveable { mutableStateOf(false) }

        DetailScreenContent(
            errorListener = {error, boolean ->
                when(error) {
                    Error.NAME -> errorStateName.value = boolean
                    Error.ADDRESS -> errorStateAddress.value = boolean
                    Error.LOGIN -> errorStateLogin.value = boolean
                    Error.PASSWORD -> errorStatePassword.value = boolean
                    Error.OKAY -> {}
                }
            },
            errorStateName = errorStateName,
            errorStateAddress = errorStateAddress,
            errorStateLogin = errorStateLogin,
            errorStatePassword = errorStatePassword,
            website = websiteState,
            onBackIconClick = onBackIconClick,

            onWebsiteStateChange = {
                websiteState.value = it
            },
            onSaveClick = {
                val websiteEntity = Website(
                    id = websiteState.value.id,
                    name = websiteState.value.name,
                    address = websiteState.value.address,
                    cipheredLogin = websiteState.value.cipheredLogin,
                    cipheredPassword = websiteState.value.cipheredPassword,
                    comment = websiteState.value.comment
                )
                val parseError = viewModel.parseWebsite(websiteEntity)
                if (parseError == Error.OKAY) {
                    viewModel.addWebsite(websiteEntity)
                    onBackIconClick()
                } else {
                    when(parseError) {
                        Error.NAME -> errorStateName.value = true
                        Error.ADDRESS -> errorStateAddress.value = true
                        Error.LOGIN -> errorStateLogin.value = true
                        Error.PASSWORD -> errorStatePassword.value = true
                        else -> {}
                    }
                }
            },
            onDeleteClick = {
                viewModel.deleteWebsite(website.id)
                onBackIconClick()
            },
            screenState = screenState
        )
    }
    else {
        val screenState = DetailScreenState.AddState
        val websiteState = rememberSaveable {
            mutableStateOf(WebsiteState(
                name = "",
                address = "",
                cipheredLogin = "",
                cipheredPassword = "",
                comment = ""
            ))
        }
        val errorStateName = rememberSaveable { mutableStateOf(false) }
        val errorStateAddress = rememberSaveable { mutableStateOf(false) }
        val errorStateLogin = rememberSaveable { mutableStateOf(false) }
        val errorStatePassword = rememberSaveable { mutableStateOf(false) }

        DetailScreenContent(
            errorListener = {error, boolean ->
                when(error) {
                    Error.NAME -> errorStateName.value = boolean
                    Error.ADDRESS -> errorStateAddress.value = boolean
                    Error.LOGIN -> errorStateLogin.value = boolean
                    Error.PASSWORD -> errorStatePassword.value = boolean
                    Error.OKAY -> {}
                }
            },
            errorStateName = errorStateName,
            errorStateAddress = errorStateAddress,
            errorStateLogin = errorStateLogin,
            errorStatePassword = errorStatePassword,
            website = websiteState,
            onBackIconClick = onBackIconClick,
            onWebsiteStateChange = {
                websiteState.value = it
            },
            onSaveClick = {
                val websiteEntity = Website(
                    name = websiteState.value.name,
                    address = websiteState.value.address,
                    cipheredLogin = websiteState.value.cipheredLogin,
                    cipheredPassword = websiteState.value.cipheredPassword,
                    comment = websiteState.value.comment
                )
                val parseError = viewModel.parseWebsite(websiteEntity)
                if (parseError == Error.OKAY) {
                    viewModel.addWebsite(websiteEntity)
                    onBackIconClick()
                } else {
                    when(parseError) {
                        Error.NAME -> errorStateName.value = true
                        Error.ADDRESS -> errorStateAddress.value = true
                        Error.LOGIN -> errorStateLogin.value = true
                        Error.PASSWORD -> errorStatePassword.value = true
                        else -> {}
                    }
                }
            },
            onDeleteClick = {},
            screenState = screenState
        )
    }
}

@Composable
fun DetailScreenContent(
    errorListener: (Error, Boolean) -> Unit,
    errorStateName: State<Boolean>,
    errorStateAddress: State<Boolean>,
    errorStateLogin: State<Boolean>,
    errorStatePassword: State<Boolean>,
    website: State<WebsiteState>,
    onBackIconClick: () -> Unit,
    onWebsiteStateChange: (WebsiteState) -> Unit,
    onSaveClick: () -> Unit,
    onDeleteClick: () -> Unit,
    screenState: DetailScreenState
) {
    Scaffold(
        topBar = { TopAppBarDetail(onBackIconClick) },
        bottomBar = { BottomAppBarDetail(onSaveClick, onDeleteClick, screenState) }
    ) {

        Column(modifier = Modifier
            .padding(it)
            .padding(horizontal = 18.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            CustomTextField(
                label = stringResource(R.string.name_website),
                text1 = website.value.name,
                onTextChange = { name ->
                    if(name.trim().isBlank()) errorListener(Error.NAME, true)
                    else errorListener(Error.NAME, false)
                    website.value.name = name
                    onWebsiteStateChange(website.value)
                },
                errorState = errorStateName
            )
            Spacer(modifier = Modifier.height(8.dp))

            CustomTextField(
                label = stringResource(R.string.website),
                text1 = website.value.address,
                onTextChange = { address ->
                    if(address.trim().isBlank()) errorListener(Error.ADDRESS, true)
                    else errorListener(Error.ADDRESS, false)
                    website.value.address = address
                    onWebsiteStateChange(website.value)
                },
                errorState = errorStateAddress
            )
            Spacer(modifier = Modifier.height(18.dp))

            AccountItem(
                website = website,
                onLoginChange = { login ->
                    if(login.trim().isBlank()) errorListener(Error.LOGIN, true)
                    else errorListener(Error.LOGIN, false)
                    website.value.cipheredLogin = login
                    onWebsiteStateChange(website.value)
                },
                onPasswordChange = { password ->
                    if(password.trim().isBlank()) errorListener(Error.PASSWORD, true)
                    else errorListener(Error.PASSWORD, false)
                    website.value.cipheredPassword = password
                    onWebsiteStateChange(website.value)
                },
                onCommentChange = { comment ->
                    website.value.comment = comment
                    onWebsiteStateChange(website.value)

                },
                errorStateLogin = errorStateLogin,
                errorStatePassword = errorStatePassword
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun CustomTextField(
    label: String, text1: String,
    onTextChange: (String) -> Unit,
    errorState: State<Boolean> = mutableStateOf(false)
) {
    val text = rememberSaveable{ mutableStateOf(text1) }

    OutlinedTextField(
        value = text.value,
        onValueChange = {  newText ->
            text.value = newText
            onTextChange(newText)
        },
        label = {Text(label)},
        modifier = Modifier.fillMaxWidth(),
        isError = errorState.value
    )
}

@Composable
private fun PasswordTextField(
    errorStatePassword: State<Boolean>,
    text1: String,
    onTextChange: (String) -> Unit
) {
    var password by rememberSaveable { mutableStateOf(text1) }
    var passwordHidden by rememberSaveable { mutableStateOf(true) }

    OutlinedTextField(
        value = password,
        onValueChange = {  newText ->
            password = newText
            onTextChange(newText)
        },
        singleLine = true,
        label = { Text(stringResource(R.string.enter_password)) },
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

@Composable
private fun AccountItem(
    errorStateLogin: State<Boolean>,
    errorStatePassword: State<Boolean>,
    website: State<WebsiteState>,
    onLoginChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onCommentChange: (String) -> Unit
) {
    Card(
        Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0x336650a4)
        ),

        ) {
        Column(
            modifier = Modifier.padding(horizontal = 10.dp),
        ) {
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = stringResource(R.string.Account)
                )
            }

            CustomTextField(
                label = stringResource(R.string.login),
                text1 = website.value.cipheredLogin,
                onTextChange = onLoginChange,
                errorState = errorStateLogin
            )
            Spacer(modifier = Modifier.height(8.dp))

            PasswordTextField(
                text1 = website.value.cipheredPassword,
                onTextChange = onPasswordChange,
                errorStatePassword = errorStatePassword
            )
            Spacer(modifier = Modifier.height(8.dp))

            CustomTextField(
                label = stringResource(R.string.comment),
                text1 = website.value.comment,
                onTextChange = onCommentChange
            )
            Spacer(modifier = Modifier.height(18.dp))
        }
    }
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


@Composable
private fun BottomAppBarDetail(
    onSaveClick: () -> Unit,
    onDeleteClick: () -> Unit,
    screenState: DetailScreenState
) {
    val height =
        if (screenState is DetailScreenState.RefactorState) 120
        else 70

    BottomAppBar(
        modifier = Modifier.height(height.dp),
        actions = {
            Column {
                Spacer(modifier = Modifier.height(5.dp))
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    onClick = { onSaveClick() }
                ) {
                    Text(text = stringResource(R.string.save_changes), color = Color.White)
                }

                if (screenState is DetailScreenState.RefactorState) {
                    Spacer(modifier = Modifier.height(5.dp))
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                        onClick = { onDeleteClick() }
                    ) {
                        Text(text = stringResource(R.string.delete), color = Color.White)
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))
            }
        }

    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopAppBarDetail(
    onBackIconClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                stringResource(R.string.websote),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            IconButton(onClick = { onBackIconClick() }) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = stringResource(R.string.close_detail_screen)
                )
            }
        },
        modifier = Modifier.shadow(elevation = 5.dp)
    )
}

enum class Error {
    NAME, ADDRESS, LOGIN, PASSWORD,
    OKAY
}