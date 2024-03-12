package com.example.passwordmanagerapp.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.unit.dp
import com.example.passwordmanagerapp.domain.entities.Website

@Composable
fun MainScreen(viewModel: MainViewModel) {

    val screenState = viewModel.screenState.collectAsState(MainScreenState.Initial)

    when (val currentState = screenState.value) {
        is MainScreenState.WebsiteList -> {
            MainScreenContent(
//                viewModel =viewModel,
                list = currentState.websiteList
            )
        }
        MainScreenState.Initial -> {}

    }
}

@Composable
fun MainScreenContent(
//    viewModel: MainViewModel,
    list: List<Website>
) {
    LazyColumn(
        contentPadding = PaddingValues(
            top = 16.dp,
            bottom = 72.dp,
            start = 8.dp,
            end = 8.dp
        ),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items = list, key = { it.id }) { item ->
            WebsiteCard(item)
        }
    }
}


@Composable
fun WebsiteCard(website: Website) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onSecondary
        )
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically
        ){
            Image(
                modifier = Modifier
                    .size(40.dp)
                    .padding(5.dp)
                    .clip(CircleShape),
                painter = ColorPainter(Color.Magenta),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = website.name)
        }
    }
}


