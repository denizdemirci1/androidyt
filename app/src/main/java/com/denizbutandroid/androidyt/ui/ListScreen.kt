package com.denizbutandroid.androidyt.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.take

@Composable
fun ListScreen(
    viewModel: ListScreenViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    Column {
        Text(
            text = if (state.isScrolled) "you scrolled" else "waiting for your scroll"
        )
        ImageList(viewModel)
    }
}

@Composable
fun ImageList(
    viewModel: ListScreenViewModel
) {
    val scrollState = rememberLazyListState()
    LazyRow(
        state = scrollState,
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(count = 20) { itemIndex ->
            Box(modifier = Modifier
                .height(50.dp)
                .width(50.dp)
                .background(Color.Black)
            ) {
                Text(
                    text = "$itemIndex",
                    color = Color.White
                )
            }
        }
    }
    LaunchedEffect(scrollState) {
        snapshotFlow { scrollState.firstVisibleItemScrollOffset }
            .distinctUntilChanged()
            .take(2) // the 2nd one is user scroll
            .drop(1)
            .collect {
                viewModel.handleUserScroll()
            }
    }
}