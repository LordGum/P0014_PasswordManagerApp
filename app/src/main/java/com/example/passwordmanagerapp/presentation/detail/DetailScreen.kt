package com.example.passwordmanagerapp.presentation.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.passwordmanagerapp.domain.entities.WebsiteAccount
import com.example.passwordmanagerapp.presentation.detail.entities_states.WebsiteState
import kotlinx.coroutines.launch


@Composable
fun DetailScreen(
    viewModel: DetailViewModel,
    websiteId: Int,
    onBackIconClick: () -> Unit
) {

    when (val screenState = viewModel.getScreenState(websiteId)) {
        is DetailScreenState.Initial -> {}
        is DetailScreenState.RefactorState -> {

            val listOfAccounts = remember {
                mutableStateListOf<WebsiteAccount>()
            }
            val websiteState =  mutableStateOf(WebsiteState(
                screenState.website.name,
                screenState.website.address,
                screenState.website.accountList
            ))

            DetailScreenContent(
                website = websiteState,
                onBackIconClick = onBackIconClick,
                listOfAccounts = listOfAccounts,
                onAccountListChange = {account, action ->
                    when(action) {
                        Action.ADD -> listOfAccounts.add(account)
                        Action.DELETE -> listOfAccounts.remove(account)
                    }
                },
                onAccountTextChange = {
                    websiteState.value.accountList = listOfAccounts.toList()
                },
                onWebsiteStateChange = {
                    websiteState.value = it
                },
                onSaveClick = {
                    val websiteEntity = Website(
                        name = websiteState.value.name,
                        address = websiteState.value.address,
                        accountList = websiteState.value.accountList as ArrayList<WebsiteAccount>
                    )
                    viewModel.addWebsite(websiteEntity)

                    onBackIconClick()
                }
            )
        }
        is DetailScreenState.AddState -> {
            val account = WebsiteAccount(
                cipherLogin = "", cipherPassword = "", comment = ""
            )
            val listOfAccounts = remember {
                mutableStateListOf(account)
            }
            val websiteState = rememberSaveable {
                mutableStateOf(WebsiteState("", "", arrayListOf(account)))
            }


            DetailScreenContent(
                website = websiteState,
                onBackIconClick = onBackIconClick,
                listOfAccounts = listOfAccounts,
                onAccountListChange = {account, action ->
                    when(action) {
                        Action.ADD -> listOfAccounts.add(account)
                        Action.DELETE -> listOfAccounts.remove(account)
                    }
                    websiteState.value.accountList = listOfAccounts.toList()
                },
                onAccountTextChange = {
                    websiteState.value.accountList = listOfAccounts.toList()
                },
                onWebsiteStateChange = {
                    websiteState.value = it
                },
                onSaveClick = {
                    val websiteEntity = Website(
                        name = websiteState.value.name,
                        address = websiteState.value.address,
                        accountList = websiteState.value.accountList as ArrayList<WebsiteAccount>
                    )

                    viewModel.addWebsite(websiteEntity)

                    onBackIconClick()
                }
            )
        }

    }
}

@Composable
fun DetailScreenContent(
    website: State<WebsiteState>,
    onBackIconClick: () -> Unit,
    listOfAccounts: List<WebsiteAccount>,
    onAccountListChange: (WebsiteAccount, Action) -> Unit,
    onAccountTextChange: (MutableList<WebsiteAccount>) -> Unit,
    onWebsiteStateChange: (WebsiteState) -> Unit,
    onSaveClick: () -> Unit
) {
    Scaffold(
        topBar = { TopAppBarDetail(onBackIconClick) },
        bottomBar = { BottomAppBarDetail(website, onAccountListChange, onSaveClick) }
    ) {
        val openBottomSheet = rememberSaveable { mutableStateOf(false) }
        var websiteAccount = if(website.value.accountList.isNotEmpty()) website.value.accountList[0]
        else WebsiteAccount(cipherLogin = "", cipherPassword = "")


        ModalBottomSheetSample(
            website = website,
            account = websiteAccount,
            openBottomSheet = openBottomSheet,
            onAccountListChange = onAccountListChange
        )

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
                    website.value.name = name
                    onWebsiteStateChange(website.value)
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
            CustomTextField(
                label = stringResource(R.string.website),
                text1 = website.value.address,
                onTextChange = { address ->
                    website.value.address = address
                    onWebsiteStateChange(website.value)
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
            ListOfAccounts(
                listOfAccounts = listOfAccounts.toMutableList(),
                onIconClickListener = { account ->
                    websiteAccount = account
                    openBottomSheet.value = true
                },
                onAccountListChange = onAccountTextChange
            )


            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun CustomTextField(
    label: String, text1: String,
    onTextChange: (String) -> Unit
) {
    val text = rememberSaveable{ mutableStateOf(text1) }

    OutlinedTextField(
        value = text.value,
        onValueChange = {  newText ->
            text.value = newText
            onTextChange(newText)
        },
        label = {Text(label)},
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun PasswordTextField(
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
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun AccountItem(
    account: WebsiteAccount,
    onIconClickListener: (WebsiteAccount) -> Unit,
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
                IconButton(
                    onClick = {
                        onIconClickListener(account)
                }) {
                    Icon(imageVector = Icons.Rounded.MoreVert, contentDescription = null)
                }
            }

            CustomTextField(
                label = stringResource(R.string.login),
                text1 = account.cipherLogin,
                onTextChange = onLoginChange
            )
            Spacer(modifier = Modifier.height(8.dp))

            PasswordTextField(
                text1 = account.cipherPassword,
                onTextChange = onPasswordChange
            )
            Spacer(modifier = Modifier.height(8.dp))

            CustomTextField(
                label = stringResource(R.string.comment),
                text1 = account.comment,
                onTextChange = onCommentChange
            )
            Spacer(modifier = Modifier.height(18.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ModalBottomSheetSample(
    website: State<WebsiteState>,
    account: WebsiteAccount,
    openBottomSheet: MutableState<Boolean>,
    onAccountListChange: (WebsiteAccount, Action) -> Unit
) {
    val skipPartiallyExpanded by remember { mutableStateOf(false) }
    val edgeToEdgeEnabled by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = skipPartiallyExpanded
    )


    if (openBottomSheet.value) {
        val windowInsets = if (edgeToEdgeEnabled)
            WindowInsets(0) else BottomSheetDefaults.windowInsets

        ModalBottomSheet(
            onDismissRequest = { openBottomSheet.value = false },
            sheetState = bottomSheetState,
            windowInsets = windowInsets
        ) {

            Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center) {
                ElevatedButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    onClick = {
                        scope.launch { bottomSheetState.hide() }.invokeOnCompletion {
                            if (!bottomSheetState.isVisible) {
                                val newAccount = WebsiteAccount(
                                    id = website.value.accountList[website.value.accountList.lastIndex].id + 1,
                                    cipherLogin = "",
                                    cipherPassword = ""
                                )
                                onAccountListChange(
                                    newAccount,
                                    Action.ADD
                                )
                                openBottomSheet.value = false
                            }
                        }
                    }
                ) {
                    Text(stringResource(R.string.add_account))
                }

                Spacer(modifier = Modifier.height(10.dp))

                ElevatedButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    onClick = {
                        scope.launch { bottomSheetState.hide() }.invokeOnCompletion {
                            if (!bottomSheetState.isVisible) {
                                onAccountListChange(
                                    account,
                                    Action.DELETE
                                )
                                openBottomSheet.value = false
                            }
                        }
                    }
                ) {
                    Text(stringResource(R.string.delete_account))
                }

                Spacer(modifier = Modifier.height(50.dp))
            }


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
    website: State<WebsiteState>,
    onAccountListChange: (WebsiteAccount, Action) -> Unit,
    onSaveClick: () -> Unit
) {
    BottomAppBar(
        modifier = Modifier.height(120.dp),
        actions = {
            Column {
                Spacer(modifier = Modifier.height(5.dp))
                TextButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    onClick = {
                        val newAccount = WebsiteAccount(
                            id = if(website.value.accountList.isNotEmpty())
                                website.value.accountList[website.value.accountList.lastIndex].id + 1
                            else 0,
                            cipherLogin = "",
                            cipherPassword = ""
                        )
                        onAccountListChange(newAccount, Action.ADD)
                    }
                ) {
                    Icon(imageVector = Icons.Rounded.Add, contentDescription = stringResource(R.string.add_account))
                    Text(text = stringResource(R.string.add_account), color = MaterialTheme.colorScheme.primary)
                }
                Spacer(modifier = Modifier.height(5.dp))
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    onClick = { onSaveClick() }
                ) {
                    Text(text = stringResource(R.string.save_changes), color = Color.White)
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

@Composable
private fun ListOfAccounts(
    listOfAccounts: MutableList<WebsiteAccount>,
    onIconClickListener: (WebsiteAccount) -> Unit,
    onAccountListChange: (MutableList<WebsiteAccount>) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 8.dp)
            .height((460 + 280 * (listOfAccounts.size - 1)).dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        userScrollEnabled = false
    ) {
        items(items = listOfAccounts, key = { it.id }) { account ->
            AccountItem (
                account = account,
                onIconClickListener = onIconClickListener,
                onLoginChange = { login ->
                    account.cipherLogin = login
                    onAccountListChange(listOfAccounts)
                },
                onPasswordChange = { password ->
                    account.cipherPassword = password
                    onAccountListChange(listOfAccounts)
                },
                onCommentChange = { comment ->
                    account.comment = comment
                    onAccountListChange(listOfAccounts)
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

enum class Action {
    DELETE, ADD
}