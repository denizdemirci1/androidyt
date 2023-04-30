package com.denizbutandroid.androidyt.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ListScreenViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ListScreenState())
    val uiState: StateFlow<ListScreenState> = _uiState.asStateFlow()

    fun handleUserScroll() {
        _uiState.value = ListScreenState(true)
    }
}

data class ListScreenState(
    val isScrolled: Boolean = false
)