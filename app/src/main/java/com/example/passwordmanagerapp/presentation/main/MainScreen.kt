package com.example.passwordmanagerapp.presentation.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.passwordmanagerapp.R
import com.example.passwordmanagerapp.domain.entities.Website

@Composable
fun MainScreen(viewModel: MainViewModel, onWebsiteClickListener: (Website) -> Unit) {

    val screenState = viewModel.screenState.collectAsState(MainScreenState.Initial)

    when (val currentState = screenState.value) {
        is MainScreenState.WebsiteList -> {
            MainScreenContent(
                viewModel =viewModel,
                list = currentState.websiteList,
                onWebsiteClickListener = onWebsiteClickListener
            )
        }
        is MainScreenState.Initial -> {
            InitialScreen(onAddClickListener = onWebsiteClickListener)
        }
    }
}

@Composable
fun MainScreenContent(
    viewModel: MainViewModel,
    list: List<Website>,
    onWebsiteClickListener: (Website) -> Unit
) {
    val listState = rememberLazyListState()
    val expandedFab = remember {
        derivedStateOf {
            listState.firstVisibleItemIndex == 0
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.onSecondary,
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    onWebsiteClickListener(
                        Website(
                            id = Website.UNDEFINED_ID,
                            "", "", "", "", "", ""
                        )
                    )
                },
                expanded = expandedFab.value,
                icon = { Icon(Icons.Filled.Add, null) },
                text = { Text(text = stringResource(R.string.add_password)) },
            )
        },
        floatingActionButtonPosition = FabPosition.End,
    ) {paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(
                top = 16.dp,
                start = 8.dp,
                end = 8.dp,
                bottom = 0.dp
            ),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items = list, key = { it.id }) { item ->
                WebsiteCard(item, onWebsiteClickListener)
            }
        }
    }
}


@Composable
fun WebsiteCard(
    website: Website,
    onWebsiteClickListener: (Website) -> Unit
) {
    OutlinedCard(
        onClick = {
            onWebsiteClickListener(website)
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onSecondary,
            contentColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically
        ){
            Image(
                modifier = Modifier
                    .padding(10.dp)
                    .size(50.dp)
                    .clip(CircleShape),
                painter = ColorPainter(Color.Magenta),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(fontSize = 20.sp, text = website.name)
        }
    }
}


@Composable
fun InitialScreen(
    onAddClickListener: (Website) -> Unit
) {
    val listState = rememberLazyListState()
    val expandedFab = remember {
        derivedStateOf {
            listState.firstVisibleItemIndex == 0
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.onSecondary,
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    onAddClickListener(
                        Website(
                            id = Website.UNDEFINED_ID,
                            "", "", "", "", "", ""
                        )
                    )
                },
                expanded = expandedFab.value,
                icon = { Icon(Icons.Filled.Add, null) },
                text = { Text(
                    color = MaterialTheme.colorScheme.primary,
                    text = stringResource(R.string.add_password)
                ) }
            )
        },
        floatingActionButtonPosition = FabPosition.End,
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center) {
            Text(
                text = stringResource(R.string.list_is_empty),
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center)
        }

    }
}