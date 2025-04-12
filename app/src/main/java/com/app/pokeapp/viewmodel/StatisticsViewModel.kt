package com.app.pokeapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis

class StatisticsViewModel : ViewModel() {

    private val _favoritesCount = MutableStateFlow(0)
    val favoritesCount: StateFlow<Int> = _favoritesCount

    private val _seenCount = MutableStateFlow(0)
    val seenCount: StateFlow<Int> = _seenCount

    private val _sessionTime = MutableStateFlow(0L)
    val sessionTime: StateFlow<Long> = _sessionTime

    private var sessionStartTime = System.currentTimeMillis()

    init {
        viewModelScope.launch {
            while (true) {
                _sessionTime.value = System.currentTimeMillis() - sessionStartTime
                kotlinx.coroutines.delay(1000)
            }
        }
    }

    fun updateFavoritesCount(count: Int) {
        _favoritesCount.value = count
    }

    fun registerPokemonSeen() {
        _seenCount.value += 1
    }
}
