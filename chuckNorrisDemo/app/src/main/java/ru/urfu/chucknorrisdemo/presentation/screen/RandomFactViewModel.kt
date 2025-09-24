package ru.urfu.chucknorrisdemo.presentation.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.urfu.chucknorrisdemo.domain.model.FactEntity
import ru.urfu.chucknorrisdemo.domain.repository.FactsRepository

class RandomFactViewModel : ViewModel() {
    private val repository = FactsRepository()

    private val _currentFact = MutableStateFlow<FactEntity?>(null)
    val currentFact: StateFlow<FactEntity?> = _currentFact.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        loadRandomFact()
    }

    fun loadRandomFact() {
        _currentFact.value = repository.getRandomFact()
    }

    fun refreshFact() {
        _isLoading.value = true
        viewModelScope.launch {
            kotlinx.coroutines.delay(1000)
            _currentFact.value = repository.getRandomFact()
            _isLoading.value = false
        }
    }
}